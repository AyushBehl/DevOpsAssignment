import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Product } from './product';
import { ProductService } from './product.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  public products: Product[] = [];
  public editProduct!: Product;
  public deleteProduct!: Product;

  constructor(private productService: ProductService){}

  ngOnInit() {
      this.getProduct();
  }

  public getProduct(): void{
    this.productService.getProduct().subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public getHistory(productId: string): void{
    this.productService.getHistory(productId).subscribe(
      (response: Product[]) => {
        this.products = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddProduct(addForm: NgForm): void {
    const add = document.getElementById('add-product-form');
    add?.click();
    this.productService.addProduct(addForm.value).subscribe(
      (response: Product) => {
        console.log(response);
        this.getProduct();
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        addForm.reset();
      }
    );
  }

  public onUpdateProduct(editForm: NgForm): void {

    this.productService.updateProduct(editForm.value).subscribe(
      (response: Product) => {
        console.log(response);
        this.getProduct();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteProduct(productId: string): void {
    this.productService.deleteProduct(productId).subscribe(
      (response: void) => {
        console.log(response);
        this.getProduct();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public searchProduct(key: string): void {
    console.log(key);
    const results: Product[] = [];
    for (const product of this.products) {
      if (product.name.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || product.description.toLowerCase().indexOf(key.toLowerCase()) !== -1) {
        results.push(product);
      }
    }
    this.products = results;
    if (results.length === 0 || !key) {
      this.getProduct();
    }
  }

  public onOpenModal(product: Product , mode: string): void {
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'edit') {
      this.editProduct = product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    if (mode === 'delete') {
      this.deleteProduct = product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
    document.getElementById('main-container')?.appendChild(button);
    button.click();
  }
  public onOpen(product: Product | null | undefined, mode: string): void {
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addProductModal');
    }
    document.getElementById('main-container')?.appendChild(button);
    button.click();
  }

  public onOpenHistory(): void {
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    button.setAttribute('data-target', '#historyProductModal');
    document.getElementById('main-container')?.appendChild(button);
    button.click();

  }
}
