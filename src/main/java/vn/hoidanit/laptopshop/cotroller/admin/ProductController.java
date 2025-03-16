package vn.hoidanit.laptopshop.cotroller.admin;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import java.util.Objects;
import java.util.Optional;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model, @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid page number");
        }
        Pageable pageable = PageRequest.of(page-1, 3);
        Page<Product> products = productService.findAll(pageable);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String handleCreateProduct(@ModelAttribute("newProduct") @Valid Product product, BindingResult bindingResult,
                                      @RequestParam("imageFile") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }
        product.setImage(uploadService.handleSaveUploadFile(file, "product"));
        productService.save(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (Objects.nonNull(product)) {
            model.addAttribute("product", product);
        }
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("product", new Product());
        return "admin/product/delete";
    }

    @PostMapping(path = "/admin/product/delete")
    public String handleDeleteUserPage(@ModelAttribute("product") Product product) {
        productService.deleteById(product.getId());
        return "redirect:/admin/product";
    }

    @GetMapping(path = "/admin/product/update/{id}")
    public String getUpdateProductPage(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        if (Objects.nonNull(product)) {
            model.addAttribute("product", product);
        }
        return "admin/product/update";
    }

    @PostMapping(path = "/admin/product/update")
    public String handleUpdateProductPage(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                          @RequestParam("imageFile") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "admin/product/update";
        }
        Product currentProduct = productService.findById(product.getId());
        if (Objects.nonNull(currentProduct)) {
            currentProduct.setName(product.getName());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setTarget(product.getTarget());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setSold(product.getSold());
            if (!currentProduct.getImage().contains(Objects.requireNonNull(file.getOriginalFilename()))) {
                currentProduct.setImage(uploadService.handleSaveUploadFile(file, "product"));
            }
            productService.save(currentProduct);
        }
        return "redirect:/admin/product";
    }
}
