package com.shunwei.oms.goods.service;

import com.shunwei.oms.goods.category.dto.GoodsCategoryDTO;
import com.shunwei.oms.goods.category.entity.GoodsCategory;
import com.shunwei.oms.goods.category.repository.GoodsCategoryRepository;
import com.shunwei.oms.goods.dto.GoodsDTO;
import com.shunwei.oms.goods.repository.GoodsRepository;
import com.shunwei.oms.goods.service.specification.GoodsSpecification;
import com.shunwei.oms.supplier.dto.SupplierDTO;
import com.shunwei.oms.supplier.entity.Supplier;
import com.shunwei.oms.supplier.repository.SupplierRepository;
import com.shunwei.oms.goods.entity.Goods;
import com.shunwei.oms.supplier.service.SupplierService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    // 正确实例化 Random 对象
    private final Random random = new Random();
    @Override
    public Page<GoodsDTO> searchGoods(Map<String, Object> searchParams, Pageable pageable) {
        Specification<Goods> specification = GoodsSpecification.bySearchParams(searchParams);

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );

        return goodsRepository.findAll(specification, sortedPageable)
                .map(this::goodsToDTO);
    }

    @Override
    public Optional<Goods> getGoodsById(Long id) {
        return goodsRepository.findById(id);
    }

    @Override
    public Goods createGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Optional<Goods> updateGoods(Long id, Goods goods) {
        if (goodsRepository.existsById(id)) {
            goods.setId(id);
            return Optional.of(goodsRepository.save(goods));
        }
        return Optional.empty();
    }

    @Override
    public void deleteGoods(List<Long> ids) {
        goodsRepository.deleteByIds(ids);
    }

    public GoodsDTO goodsToDTO(Goods goods) {
        GoodsDTO dto = new GoodsDTO();
        dto.setId(goods.getId());
        dto.setInternalCode(goods.getInternalCode());
        dto.setExternalCode(goods.getExternalCode());
        dto.setName(goods.getName());
        dto.setCategory(goods.getCategory());
        dto.setPicture(goods.getPicture());
        dto.setBrand(goods.getBrand());
        dto.setDetails(goods.getDetails());
        dto.setUsageLocation(goods.getUsageLocation());
        dto.setUnit(goods.getUnit());
        dto.setBoxStandards(goods.getBoxStandards());
        dto.setCostPrice(goods.getCostPrice());
        dto.setSellingPrice(goods.getSellingPrice());
        dto.setGrossMargin(goods.getGrossMargin());
        dto.setLeadTime(goods.getLeadTime());
        dto.setMoq(goods.getMoq());
        dto.setRemark(goods.getRemark());
        dto.setCreatedAt(goods.getCreatedAt());
        dto.setUpdatedAt(goods.getUpdatedAt());

        // 先判断是否有 supplierId
        if (goods.getSupplierId() != null && goods.getSupplierId() > 0) {
            // 如果有 supplierId，再去获取 Supplier 实体并转换为 DTO
            Optional<Supplier> supplierOpt = supplierRepository.findById(goods.getSupplierId());
            supplierOpt.ifPresent(supplier -> {
                SupplierDTO supplierDTO = new SupplierDTO();
                supplierDTO.setId(supplier.getId());
                supplierDTO.setName(supplier.getName());
                dto.setSupplier(supplierDTO); // 将 SupplierDTO 赋值给 GoodsDTO
            });
        } else {
            // 如果没有 supplierId，返回空的 SupplierDTO
            dto.setSupplier(null); // 或者返回一个新的空 SupplierDTO，例如 new SupplierDTO()
        }

        // 先判断是否有 goodsCategoryId
        if (goods.getGoodsCategoryId() != null && goods.getGoodsCategoryId() > 0) {
            // 如果有 goodsCategoryId，再去获取 goodsCategory 实体并转换为 DTO
            Optional<GoodsCategory> goodsCategoryOpt = goodsCategoryRepository.findById(goods.getGoodsCategoryId());
            goodsCategoryOpt.ifPresent(goodsCategory -> {
                GoodsCategoryDTO goodsCategoryDTO = new GoodsCategoryDTO();
                goodsCategoryDTO.setId(goodsCategory.getId());
                goodsCategoryDTO.setParentCategory(goodsCategory.getParentCategory());  // 设置父类
                goodsCategoryDTO.setCategory(goodsCategory.getCategory());  // 设置中类
                goodsCategoryDTO.setSubCategory(goodsCategory.getSubCategory());  // 设置小类
                goodsCategoryDTO.setName(goodsCategory.getName());  // 设置名称
                goodsCategoryDTO.setAttributes(goodsCategory.getAttributes());  // 设置属性
                goodsCategoryDTO.setOthers(goodsCategory.getOthers());  // 设置其他
                goodsCategoryDTO.setRemark(goodsCategory.getRemark());  // 设置备注
                dto.setGoodsCategory(goodsCategoryDTO);
            });
        } else {
            // 如果没有 goodsCategoryId，则插入进去一条新的到goodsCategor
            dto.setGoodsCategory(null);
        }

        return dto;
    }

    @Override
    public void importGoodsFromExcel(MultipartFile file) throws IOException {
        List<Goods> goodsList = new ArrayList<>();
        List<String> errorDetails = new ArrayList<>();  // 用于记录错误信息

        // 读取 Excel 文件
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            // 校验表头
            String[] expectedHeaders = {
                    "序号", "商品内部编码", "商品外部编码", "大类", "中类", "小类", "产品名称", "产品图片",
                    "产品品牌", "酒店适用品牌", "型号/规格/容量/颜色", "使用位置", "单位", "箱规", "成本价", "销售价",
                    "毛利率", "供货周期", "供应商名称", "供应商编码", "起订量"
            };

            Row headerRow = sheet.getRow(0);
            for (int i = 0; i < expectedHeaders.length; i++) {
                if (!getCellValue(headerRow.getCell(i)).equals(expectedHeaders[i])) {
                    throw new IllegalArgumentException("表头不正确，期望: " + Arrays.toString(expectedHeaders));
                }
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过表头

                try {
                    Goods goods = new Goods();

                    // 检查列数
                    if (row.getPhysicalNumberOfCells() < expectedHeaders.length) {
                        throw new IllegalArgumentException("第 " + (row.getRowNum() + 1) + " 行列数不足，期望 " + expectedHeaders.length + " 列");
                    }

                    // 读取 Excel 行的值并设置到 Goods 对象
                    goods.setInternalCode("INT-" + random.nextInt(100000));
                    goods.setExternalCode(getCellValue(row.getCell(2))); //商品外部编码
                    goods.setName(getCellValue(row.getCell(6))); //产品名称

                    // 查询数据库以商品是否已存在
                    if (goodsRepository.existsByName(goods.getName())) {
                        System.out.println("行 " + row.getRowNum() + ": 商品名称 '" + goods.getName() + "' 已存在，跳过当前行");
                        continue; // 跳过当前行
                    }
                    goods.setBrand(getCellValue(row.getCell(8))); //产品品牌
                    //goods.setBrand(getCellValue(row.getCell(9))); //酒店适用品牌
                    goods.setDetails(getCellValue(row.getCell(10))); //型号/规格/容量/颜色

                    goods.setUsageLocation(getCellValue(row.getCell(11)));  //使用位置
                    goods.setUnit(getCellValue(row.getCell(12))); //单位
                    goods.setBoxStandards(getCellValue(row.getCell(13))); //箱规

                    // 转换价格等字段，处理可能的异常
                    goods.setCostPrice(parseFloatSafe(getCellValue(row.getCell(14)))); //成本价
                    goods.setSellingPrice(parseFloatSafe(getCellValue(row.getCell(15)))); //销售价
                    goods.setGrossMargin(parseFloatSafe(getCellValue(row.getCell(16)))); //毛利率
                    goods.setLeadTime(getCellValue(row.getCell(17))); //供货周期
                    goods.setMoq(parseIntSafe(getCellValue(row.getCell(20)))); //供货周期

                    // 查询 goods_category 表以设置 goodsCategoryId
                    Long categoryId = findCategoryId(
                            getCellValue(row.getCell(3)),  // 大类
                            getCellValue(row.getCell(4)),  // 中类
                            getCellValue(row.getCell(5))    // 小类
                    );

                    // 仅在找到有效的 categoryId 时设置
                    if (categoryId == null) {
                        // 创建新的 GoodsCategory 实体
                        GoodsCategory newGoodsCategory = new GoodsCategory();
                        newGoodsCategory.setParentCategory(getCellValue(row.getCell(3)));  // 设置大类
                        newGoodsCategory.setCategory(getCellValue(row.getCell(4)));  // 设置中类
                        newGoodsCategory.setSubCategory(getCellValue(row.getCell(5)));  // 设置小类
                        newGoodsCategory.setName(getCellValue(row.getCell(3)));
                        // 保存新的 GoodsCategory 到数据库
                        GoodsCategory savedGoodsCategory = goodsCategoryRepository.save(newGoodsCategory);
                        // 获取保存后的 ID
                        categoryId = savedGoodsCategory.getId();
                    }
                    goods.setGoodsCategoryId(categoryId);

                    // 查询 suppliers 表以设置 supplierId
                    Long supplierId = findSupplierId(getCellValue(row.getCell(18))); // 供应商名称

                    // 仅在找到有效的 supplierId 时设置
                    if (supplierId == null) {
                        // 如果没有供应商，创建一个新的供应商
                        Supplier newSupplier = new Supplier();
                        newSupplier.setName(getCellValue(row.getCell(18))); // 假设你有供应商名称的变量 supplierName
                        // 这里可以设置其他供应商相关字段
                        newSupplier.setCode("CODE-" + random.nextInt(100000));
                        newSupplier.setCreatedAt(LocalDateTime.now());
                        newSupplier.setUpdatedAt(LocalDateTime.now());
                        // 保存供应商到数据库
                        supplierRepository.save(newSupplier);
                        // 获取保存后的供应商ID
                        supplierId = newSupplier.getId();
                    }
                    goods.setSupplierId(supplierId);

                    goodsList.add(goods);

                } catch (Exception e) {
                    // 捕获每行中的异常并记录错误信息
                    String errorMessage = String.format("第 %d 行发生错误: %s", row.getRowNum() + 1, e.getMessage());
                    errorDetails.add(errorMessage);  // 将错误添加到错误列表
                    continue;  // 继续处理下一个行
                }
            }
        }

        // 保存商品到数据库
        if (!goodsList.isEmpty()) {
            goodsRepository.saveAll(goodsList);
        }

        // 如果有错误，则输出错误详细信息
        if (!errorDetails.isEmpty()) {
            throw new IllegalStateException("导入过程中出现以下错误: " + String.join("; ", errorDetails));
        }
    }

    /**
     * 获取 Excel 单元格的值，根据单元格的不同类型进行处理
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    // 根据公式的返回类型获取值
                    if (cell.getCachedFormulaResultType() == CellType.STRING) {
                        return cell.getStringCellValue();  // 公式返回字符串
                    } else if (cell.getCachedFormulaResultType() == CellType.NUMERIC) {
                        return String.valueOf(cell.getNumericCellValue());  // 公式返回数值
                    }
                } catch (IllegalStateException e) {
                    return "Error in Formula";  // 捕获公式错误
                }
            default:
                return null;
        }
    }

    /**
     * 解析安全的 Float 类型值
     */

    public Float parseFloatSafe(String value) {
        try {
            if (value == null || value.trim().isEmpty()) {
                return 0.0f; // 或者你可以返回一个合适的默认值
            }
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            // 如果解析失败，也返回默认值或根据业务需求处理
            return 0.0f;
        }
    }


    /**
     * 解析安全的 Integer 类型值
     */
    public Integer parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // 或者返回默认值，例如 0
        }
        try {
            // 先处理带小数点的情况
            if (value.contains(".")) {
                // 将 "1.0" 转换为 "1"
                value = value.substring(0, value.indexOf("."));
            }
            return Integer.parseInt(value.trim()); // 转换为整数
        } catch (NumberFormatException e) {
            // 处理异常
            return null; // 或者返回默认值
        }
    }



    // 查找 goods_category 表
    private Long findCategoryId(String parentCategory, String category, String subCategory) {
        // 使用 goodsCategoryRepository 或类似的服务查找 categoryId
        return goodsCategoryRepository.findIdByDetails(parentCategory, category, subCategory);
    }

    // 查找 suppliers 表
    private Long findSupplierId(String supplierName) {
        // 使用 supplierRepository 或类似的服务查找 supplierId
        return supplierRepository.findIdByName(supplierName);
    }

}
