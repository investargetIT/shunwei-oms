package com.shunwei.oms.supplier.service;

import com.shunwei.oms.supplier.entity.Supplier;
import com.shunwei.oms.supplier.repository.SupplierRepository;
import com.shunwei.oms.supplier.service.specification.SupplierSpecification;
import com.shunwei.oms.common.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Override
    public Optional<Supplier> findSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    @Override
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public Optional<Supplier> updateSupplier(Long id, Supplier supplier) {
        if (supplierRepository.existsById(id)) {
            supplier.setId(id);
            return Optional.of(supplierRepository.save(supplier));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteSupplier(Long id) {
        if (supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void deleteSuppliers(List<Long> ids) {
        supplierRepository.deleteByIds(ids);
    }

    // 实现动态搜索
    @Override
    public Page<Supplier> searchSuppliers(Map<String, Object> searchParams, Pageable pageable) {
        Specification<Supplier> spec = Specification.where(null);

        if (searchParams.containsKey("name")) {
            spec = spec.and(SupplierSpecification.hasName((String) searchParams.get("name")));
        }
        if (searchParams.containsKey("code")) {
            spec = spec.and(SupplierSpecification.hasCode((String) searchParams.get("code")));
        }
        if (searchParams.containsKey("bankAccount")) {
            spec = spec.and(SupplierSpecification.hasBankAccount((String) searchParams.get("bankAccount")));
        }
        if (searchParams.containsKey("partnershipCase")) {
            spec = spec.and(SupplierSpecification.hasPartnershipCase((String) searchParams.get("partnershipCase")));
        }
        if (searchParams.containsKey("attribute")) {
            spec = spec.and(SupplierSpecification.hasAttribute((String) searchParams.get("attribute")));
        }
        if (searchParams.containsKey("mode")) {
            spec = spec.and(SupplierSpecification.hasMode((String) searchParams.get("mode")));
        }
        if (searchParams.containsKey("hotel")) {
            spec = spec.and(SupplierSpecification.hasHotel((String) searchParams.get("hotel")));
        }
        if (searchParams.containsKey("status")) {
            spec = spec.and(SupplierSpecification.hasStatus((String) searchParams.get("status")));
        }
        if (searchParams.containsKey("contact")) {
            spec = spec.and(SupplierSpecification.hasContact((String) searchParams.get("contact")));
        }
        if (searchParams.containsKey("position")) {
            spec = spec.and(SupplierSpecification.hasPosition((String) searchParams.get("position")));
        }
        if (searchParams.containsKey("telephone")) {
            spec = spec.and(SupplierSpecification.hasTelephone((String) searchParams.get("telephone")));
        }
        if (searchParams.containsKey("salesman")) {
            spec = spec.and(SupplierSpecification.hasSalesman((String) searchParams.get("salesman")));
        }
        if (searchParams.containsKey("contractStatus")) {
            spec = spec.and(SupplierSpecification.hasContractStatus((String) searchParams.get("contractStatus")));
        }
        if (searchParams.containsKey("dealDate")) {
            spec = spec.and(SupplierSpecification.hasDealDate((LocalDate) searchParams.get("dealDate")));
        }
        if (searchParams.containsKey("startDate")) {
            LocalDate startDate = DateUtils.parseLocalDate((String) searchParams.get("startDate"));
            spec = spec.and(SupplierSpecification.hasStartDate(startDate));
        }
        if (searchParams.containsKey("endDate")) {
            LocalDate endDate = DateUtils.parseLocalDate((String) searchParams.get("endDate"));
            spec = spec.and(SupplierSpecification.hasEndDate(endDate));
        }
        if (searchParams.containsKey("remark")) {
            spec = spec.and(SupplierSpecification.hasRemark((String) searchParams.get("remark")));
        }
        if (searchParams.containsKey("createdAt")) {
            LocalDateTime createdAt = DateUtils.parseLocalDateTime((String) searchParams.get("createdAt"));
            spec = spec.and(SupplierSpecification.hasCreatedAt(createdAt));
        }
        if (searchParams.containsKey("updatedAt")) {
            spec = spec.and(SupplierSpecification.hasUpdatedAt((LocalDateTime) searchParams.get("updatedAt")));
        }

        // 创建新的 Pageable，按 updatedAt 倒序排序
        Pageable sortedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "updatedAt") // 按更新时间倒序
        );
        return supplierRepository.findAll(spec, sortedPageable);
    }

    @Override
    public List<Supplier> findAllSuppliers() {
        return supplierRepository.findAll();
    }


    @Override
    public void importSuppliersFromExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // 校验表头
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Excel 文件没有表头行");
            }
            List<String> expectedHeaders = Arrays.asList(
                    "供应商名称", "大类", "中类", "小类", "银行账户信息", "合作案例",
                    "供应商属性", "合作模式", "销售范围（酒店）", "状态", "供应商联系人",
                    "职位", "电话", "对接人", "合同状态", "签订日期",
                    "合同约定生效日期", "合同约定终止日期"
            );

            for (int i = 0; i < expectedHeaders.size(); i++) {
                Cell cell = headerRow.getCell(i);
                if (cell == null || !expectedHeaders.get(i).equals(cell.getStringCellValue())) {
                    throw new IllegalArgumentException("表头与预期不符，错误在列 " + (i + 1));
                }
            }

            // 处理每一行数据
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                // 检查列数
                if (row.getPhysicalNumberOfCells() < 18) {
                    System.err.println("行 " + row.getRowNum() + ": 列数不足，跳过当前行");
                    continue;
                }

                Supplier.SupplierBuilder supplierBuilder = Supplier.builder();
                String generatedCode = UUID.randomUUID().toString();
                supplierBuilder.code(generatedCode);

                String name = getStringCellValue(row.getCell(0));
                if (name == null || name.isEmpty()) {
                    System.err.println("行 " + row.getRowNum() + ": 供应商名称不能为空");
                    continue;
                }

                if (supplierRepository.existsByName(name)) {
                    System.out.println("行 " + row.getRowNum() + ": 供应商名称 '" + name + "' 已存在，跳过当前行");
                    continue;
                }
                supplierBuilder.name(name);

                // 处理其他字段
                supplierBuilder.bankAccount(getStringCellValue(row.getCell(4)));
                supplierBuilder.partnershipCase(getStringCellValue(row.getCell(5)));
                supplierBuilder.attribute(getStringCellValue(row.getCell(6)));
                supplierBuilder.mode(getStringCellValue(row.getCell(7)));
                supplierBuilder.hotel(getStringCellValue(row.getCell(8)));
                supplierBuilder.status(getStringCellValue(row.getCell(9)));
                supplierBuilder.contact(getStringCellValue(row.getCell(10)));
                supplierBuilder.position(getStringCellValue(row.getCell(11)));
                String phoneNumber = getCellValue(row.getCell(12));
                if (phoneNumber.length() > 10) {
                    // 可能是科学计数法，需要处理
                    phoneNumber = new BigDecimal(phoneNumber).toPlainString();
                }
                supplierBuilder.telephone(phoneNumber);
                supplierBuilder.salesman(getStringCellValue(row.getCell(13)));
                supplierBuilder.contractStatus(getStringCellValue(row.getCell(14)));
                supplierBuilder.dealDate(getLocalDate(row.getCell(15)));
                supplierBuilder.startDate(getLocalDate(row.getCell(16)));
                supplierBuilder.endDate(getLocalDate(row.getCell(17)));

                Supplier supplier = supplierBuilder.build();
                saveSupplier(supplier);
            }
        } catch (Exception e) {
            throw new RuntimeException("导入 Excel 文件失败: " + e.getMessage());
        }
    }


    // 辅助方法：获取字符串单元格值
    private String getStringCellValue(Cell cell) {
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        }
        return null; // 或者根据需要返回默认值
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // 如果单元格为空，返回空字符串
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue(); // 字符串类型
            case NUMERIC:
                // 处理数字类型，返回字符串
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()); // 布尔类型
            case FORMULA:
                return cell.getCellFormula(); // 公式类型
            default:
                return ""; // 其他类型返回空字符串
        }
    }

    // 辅助方法：将 Excel 日期转换为 LocalDate
    private LocalDate getLocalDate(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    return LocalDate.parse(cell.getStringCellValue()); // 尝试解析字符串为 LocalDate
                } catch (DateTimeParseException e) {
                    throw new IllegalArgumentException("无效日期格式: " + cell.getStringCellValue());
                }
            }
        }
        return null; // 返回 null 或者根据需要抛出异常
    }

}
