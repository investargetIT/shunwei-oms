package com.yeebotech.shunweioms.goods.service;

import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.goods.repository.GoodsRepository;
import com.yeebotech.shunweioms.goods.service.specifications.GoodsSpecifications;
import com.yeebotech.shunweioms.supplier.dto.SupplierDTO;
import com.yeebotech.shunweioms.supplier.entity.Supplier;
import com.yeebotech.shunweioms.supplier.repository.SupplierRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.yeebotech.shunweioms.goods.dto.GoodsDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Page<GoodsDTO> searchGoods(Map<String, Object> searchParams, Pageable pageable) {
        Specification<Goods> specification = GoodsSpecifications.bySearchParams(searchParams);
        return goodsRepository.findAll(specification, pageable)
                .map(this::convertToDTO);
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

    private GoodsDTO convertToDTO(Goods goods) {
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

        // 通过 supplierId 获取 Supplier 实体并转换为 DTO
        Optional<Supplier> supplierOpt = supplierRepository.findById(goods.getSupplierId());
        supplierOpt.ifPresent(supplier -> {
            SupplierDTO supplierDTO = new SupplierDTO();
            supplierDTO.setId(supplier.getId());
            supplierDTO.setName(supplier.getName());
            dto.setSupplier(supplierDTO); // 将 SupplierDTO 赋值给 GoodsDTO
        });

        return dto;
    }

}
