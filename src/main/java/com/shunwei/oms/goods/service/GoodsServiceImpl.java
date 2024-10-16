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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

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
            // 如果没有 supplierId，返回空的 SupplierDTO
            dto.setGoodsCategory(null); // 或者返回一个新的空 SupplierDTO，例如 new SupplierDTO()
        }

        return dto;
    }

    @Override
    public void importGoodsFromExcel(MultipartFile file) throws IOException {
        List<Goods> goodsList = new ArrayList<>();

        // 读取 Excel 文件
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // 跳过表头
                Goods goods = new Goods();

                // 读取 Excel 行的值并设置到 Goods 对象
                goods.setInternalCode(getCellValue(row.getCell(1)));
                goods.setExternalCode(getCellValue(row.getCell(2)));
                goods.setName(getCellValue(row.getCell(6)));
                goods.setBrand(getCellValue(row.getCell(8)));
                goods.setUsageLocation(getCellValue(row.getCell(10)));
                goods.setUnit(getCellValue(row.getCell(11)));
                goods.setBoxStandards(getCellValue(row.getCell(12)));

                // 转换价格等字段，处理可能的异常
                goods.setCostPrice(parseFloatSafe(getCellValue(row.getCell(13))));
                goods.setSellingPrice(parseFloatSafe(getCellValue(row.getCell(14))));
                goods.setGrossMargin(parseFloatSafe(getCellValue(row.getCell(15))));
                goods.setLeadTime(getCellValue(row.getCell(16)));

                // 使用 parseIntSafe 处理最小订购量
                Integer moq = parseIntSafe(getCellValue(row.getCell(18)));
                if (moq != null) {
                    goods.setMoq(moq); // 这里 moq 是 Integer 类型
                } else {
                    continue; // 跳过当前行或记录日志
                }

                // 查询 goods_category 表以设置 goodsCategoryId
                Long categoryId = findCategoryId(
                        getCellValue(row.getCell(3)),  // 分类
                        getCellValue(row.getCell(4)),  // 型号/规格
                        getCellValue(row.getCell(5))    // 容量/颜色
                );

                // 仅在找到有效的 categoryId 时设置
                if (categoryId != null) {
                    goods.setGoodsCategoryId(categoryId);
                } else {
                    continue; // 跳过当前行，或记录日志以便调试
                }

                // 查询 suppliers 表以设置 supplierId
                Long supplierId = findSupplierId(getCellValue(row.getCell(17))); // 供应商名称

                // 仅在找到有效的 supplierId 时设置
                if (supplierId != null) {
                    goods.setSupplierId(supplierId);
                } else {
                    continue; // 跳过当前行，或记录日志以便调试
                }

                goodsList.add(goods);
            }
        }

        // 保存商品到数据库
        goodsRepository.saveAll(goodsList);
    }

    // 辅助方法，确保 Cell 值不会为 null
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        return cell.getCellType() == CellType.NUMERIC
                ? String.valueOf((int) cell.getNumericCellValue())
                : cell.getStringCellValue();
    }

    // 安全地转换为 float 类型，处理空字符串和异常
    private float parseFloatSafe(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0f; // 默认值为 0
        }
    }

    // 安全地转换为 int 类型
    private Integer parseIntSafe(String value) {
        if (value == null || value.isEmpty()) {
            return null; // 或者返回默认值，比如 0，视需求而定
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // 处理转换失败的情况，可以记录日志或抛出自定义异常
            return null; // 或者根据需求抛出异常
        }
    }

    // 查找 goods_category 表
    private Long findCategoryId(String category, String spec, String capacity) {
        // 使用 goodsCategoryRepository 或类似的服务查找 categoryId
        return goodsCategoryRepository.findIdByDetails(category, spec, capacity);
    }

    // 查找 suppliers 表
    private Long findSupplierId(String supplierName) {
        // 使用 supplierRepository 或类似的服务查找 supplierId
        return supplierRepository.findIdByName(supplierName);
    }

}
