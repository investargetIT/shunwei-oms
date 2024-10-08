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


}
