package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public String allProducts(Model model,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
                              @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {

        Sort sort = Sort.by("id").ascending();

        PageRequest pageRequest = PageRequest.of(page.orElse(1) - 1, size.orElse(5), sort);

        Specification<Product> spec = ProductSpecification.trueLiteral();

        if (title != null && !title.isEmpty()) {
            spec = spec.and(ProductSpecification.titleLike(title));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.greaterThanMinPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.lessThanMaxPrice(maxPrice));
        }

        model.addAttribute("productsPage", productRepository.findAll(spec, pageRequest));
        return "products";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product"));
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
    public String deleteProduct(@PathVariable("id") Integer id) {
        productRepository.deleteById(id);
        return "redirect:/product";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFoundExceptionHandler(NotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.getModel().put("entity_name", exception.getMessage());
        return modelAndView;
    }
}
