package vn.hoidanit.laptopshop.service.specification;

import org.springframework.data.jpa.domain.Specification;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

import java.util.List;

public class ProductSpecifications {

    public static Specification<Product> matchList(List<String> list, String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(field)).value(list));
    }

    public static Specification<Product> matchPrice(double min, double max) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max));
    }
}
