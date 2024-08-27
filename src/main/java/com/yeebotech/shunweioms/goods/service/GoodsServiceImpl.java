package com.yeebotech.shunweioms.goods.service;

import com.yeebotech.shunweioms.goods.entity.Goods;
import com.yeebotech.shunweioms.goods.repository.GoodsRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public Page<Goods> searchGoods(Map<String, Object> searchParams, Pageable pageable) {
        Specification<Goods> specification = (Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchParams != null && !searchParams.isEmpty()) {
                searchParams.forEach((key, value) -> {
                    if (value != null && StringUtils.hasText(value.toString())) {
                        switch (key) {
                            case "internalCode":
                                predicates.add(criteriaBuilder.equal(root.get("internalCode"), value));
                                break;
                            case "externalCode":
                                predicates.add(criteriaBuilder.equal(root.get("externalCode"), value));
                                break;
                            case "name":
                                predicates.add(criteriaBuilder.like(root.get("name"), "%" + value + "%"));
                                break;
                            case "category":
                                predicates.add(criteriaBuilder.equal(root.get("category"), value));
                                break;
                            case "brand":
                                predicates.add(criteriaBuilder.equal(root.get("brand"), value));
                                break;
                            // Add more cases for other fields if needed
                        }
                    }
                });
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return goodsRepository.findAll(specification, pageable);
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
}
