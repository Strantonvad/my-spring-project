package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                              @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {

        List<Product> allProducts;
        if((title == null || title.isEmpty()) && (minPrice == null && maxPrice == null)) {
            allProducts = productRepository.findAll();
        } else if (maxPrice != null && minPrice == null ) {
            allProducts = productRepository.findByCostLessThanEqual(maxPrice);
        } else if (minPrice != null && maxPrice == null ) {
            allProducts = productRepository.findByCostGreaterThan(minPrice);
        } else if (minPrice != null && maxPrice != null) {
            allProducts = productRepository.findByCostBetween(minPrice, maxPrice);
        } else {
            allProducts = productRepository.findByTitleLike("%" + title + "%");
        }
        model.addAttribute("products", allProducts);
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "product";
    }

    @GetMapping("/new")
    public String newProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "product";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product";
        }

        productRepository.save(product);
        return "redirect:/product";
    }

    @DeleteMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Integer id) throws SQLException {
        productRepository.deleteById(id);
        return "redirect:/product";
    }
}
