package com.example.ims.controller;

import java.util.List; 
import java.util.Optional; 
import java.util.Random; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import com.example.ims.repo.ProductRepo;
import com.example.ims.model.Product; 
import com.example.ims.service.ProductService; 

@Controller
public class ProductController { 

    @Autowired
    private ProductService productService; 

    @GetMapping("/") 
    public String getIndexPage(Model model, Authentication authentication) { 
        String userName = authentication.getName(); // Get the logged-in user's username
        List<Product> productList = productService.getProductsForUser(userName); 
        model.addAttribute("products", productList); 
        model.addAttribute("product", new Product()); 
        return "index"; 
    } 

    @PostMapping("/newProduct") 
    public String newProduct(@ModelAttribute("product") Product product, 
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) { 
        String userName = authentication.getName(); // Get the logged-in user's username
        // Creating dynamic product ID 
        String productId = "PD" + (1000 + new Random().nextInt(9000)); 
        product.setId(productId); 
        product.setUserName(userName); // Set the username for the product
        productService.addProduct(product); 
        redirectAttributes.addFlashAttribute("insertSuccess", true); 
        return "redirect:/"; 
    } 

    @PostMapping("/searchProduct") 
    public String searchProduct(@RequestParam(name = "id") String id, 
                                RedirectAttributes redirectAttributes, 
                                Model model,
                                Authentication authentication) { 
        Optional<Product> foundProduct = productService.getProductById(id);
        String userName = authentication.getName(); // Get the logged-in user's username

        if (foundProduct.isPresent() && foundProduct.get().getUserName().equals(userName)) { 
            redirectAttributes.addFlashAttribute("foundProduct", foundProduct); 
        } else { 
            redirectAttributes.addFlashAttribute("notFound", true); 
        } 

        model.addAttribute("product", new Product()); 
        return "redirect:/"; 
    } 

    @PostMapping("/deleteProduct") 
    public String deleteProduct(@RequestParam(name = "id") String id, 
                                RedirectAttributes redirectAttributes,
                                Authentication authentication) { 
        Optional<Product> foundProduct = productService.getProductById(id);
        String userName = authentication.getName(); // Get the logged-in user's username

        if (foundProduct.isPresent() && foundProduct.get().getUserName().equals(userName)) {
            boolean deleteSuccess = productService.deleteProductById(id); 
            if (deleteSuccess) { 
                redirectAttributes.addFlashAttribute("deleteSuccess", true); 
            } else { 
                redirectAttributes.addFlashAttribute("notFound", true); 
            }
        } else { 
            redirectAttributes.addFlashAttribute("notFound", true); 
        }

        return "redirect:/"; 
    } 

    @GetMapping("/productAnalysis") 
    public String productAnalysis(Model model, Authentication authentication) { 
        String userName = authentication.getName(); // Get the logged-in user's username
        List<Product> products = productService.getProductsForUser(userName); 
        model.addAttribute("products", products); 
        return "productAnalysis"; // Thymeleaf template name 
    } 
}
