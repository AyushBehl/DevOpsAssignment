package com.c4c.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.beans.BeanUtils.copyProperties;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/all")
    public ResponseEntity<List<product>> getAllProducts(){
        List<product> productList=new ArrayList<>();
        List<ProductSave> dbProduct = productRepository.findAll();
        int i;
        for(i=0; i<dbProduct.size(); i++)
        {
            product localProduct=new product();
            ProductSave currProduct= new ProductSave();
            ProductHistory history=new ProductHistory();
            copyProperties(dbProduct.get(i),currProduct);
            localProduct.setProductId(currProduct.getProductId());
            localProduct.setName(currProduct.getName());
            List<ProductHistory> productHis=new ArrayList<>();
            productHis=currProduct.getProductHistory();
            history=productHis.get(0);
            localProduct.setUserName(history.getUserName());
            localProduct.setUserId(history.getUserID());
            localProduct.setDescription(currProduct.getDescription());
            localProduct.setImgUrl(currProduct.getImgUrl());
            productList.add(localProduct);
        }

        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<List<ProductHistory>> getHistory(@PathVariable String id){
        Optional<ProductSave> ps = productRepository.findById(id);
        List<ProductHistory> productHis=ps.get().getProductHistory();
        return new ResponseEntity<>(productHis, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public Optional<ProductSave> getAllProductsById(@PathVariable String id){
        return productRepository.findById(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductSave> addProduct(@RequestBody product product){

        ProductSave ProductSave =new ProductSave();
        ProductSave.setName(product.getName());
        ProductSave.setImgUrl(product.getImgUrl());
        ProductSave.setDescription(product.getDescription());

        List<ProductHistory> productHistory=new ArrayList<>();
        //productHistory= ProductSave.getProductHistory();
        ProductHistory newHistory=new ProductHistory();
        newHistory.setUserID(product.getUserId());
        newHistory.setUserName(product.getUserName());
        productHistory.add(newHistory);
        ProductSave.setProductHistory(productHistory);
        ProductSave newProduct = productRepository.save(ProductSave);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductSave> deleteProduct(@PathVariable String id){
        productRepository.deleteById(id);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductSave> updateProduct(@RequestBody product product) throws Exception {
        String id=product.getProductId();
        ProductSave ProductSave = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found for this id :: " + id));
        ProductSave.setName(product.getName());
        ProductSave.setImgUrl(product.getImgUrl());
        ProductSave.setDescription(product.getDescription());

        List<ProductHistory> productHistory=new ArrayList<>();
        productHistory= ProductSave.getProductHistory();
        ProductHistory newHistory=new ProductHistory();
        newHistory.setUserID(product.getUserId());
        newHistory.setUserName(product.getUserName());

        productHistory.add(newHistory);
        ProductSave.setProductHistory(productHistory);
        final ProductSave updateProduct = productRepository.save(ProductSave);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }

}
