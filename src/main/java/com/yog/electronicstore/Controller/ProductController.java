package com.yog.electronicstore.Controller;

import com.yog.electronicstore.Dtos.ApiResponseMessage;
import com.yog.electronicstore.Dtos.PageableResponse;
import com.yog.electronicstore.Dtos.ProductDto;
import com.yog.electronicstore.Service.FileService;
import com.yog.electronicstore.Service.ProductService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@RequestMapping("/products")
@Builder
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

    /**
     * @author Yogesh Shelar
     * @param productDto
     * @return
     */
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        log.info(" Initiated Request for creating product");
        ProductDto dto = this.productService.create(productDto);
        log.info(" completed Request for creating product");
        return new ResponseEntity<ProductDto>(dto, HttpStatus.CREATED);
    }

    /**
     * @author Rinku Patil
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Request  for service layer to get All product Information ");
        return new ResponseEntity<>(productService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    /**
     * @author Rinku Patil
     * @param productDto
     * @param productId
     * @return
     */
//Update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto productDto,
                                                    @PathVariable String productId) {
        log.info(" Initiated Request for updating product with productId :{}", productId);
        ProductDto updatedproduct = this.productService.update(productDto, productId);
        return new ResponseEntity(updatedproduct, HttpStatus.OK);

    }

    /**
     * @author Yogesh Shelar
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId) {
        log.info(" Initiated Request for getting product with productId :{}", productId);
        ProductDto dto = this.productService.get(productId);
        log.info(" completed Request for getting product with productId :{}", productId);
        return new ResponseEntity<ProductDto>(dto, HttpStatus.OK);
    }

    /**
     * @author Rinku Patil
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId) {
        log.info(" Initiated Request for deleting productId with productId :{}", productId);
        this.productService.delete(productId);
        ApiResponseMessage message = ApiResponseMessage
                .builder()
                .message("User is deleted Sucessfully!!  ")
                .success(true).
                status(HttpStatus.OK)
                .build();
        log.info(" completing Request for deleting product with productId :{}", productId);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    /**
     * @author Yogesh  Shelar
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Request  for service layer to get All product Information ");
        return new ResponseEntity<>(productService.getAllLive(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * @author  Yogesh Shelar
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "PageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        log.info("Request  for service layer to get All product Information ");
        return new ResponseEntity<>(productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * @author  Yogesh Shelar
     * @param image
     * @param productId
     * @return
     * @throws IOException
     */

    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage") MultipartFile image, @PathVariable String productId) throws IOException {
        {
            log.info("Request Starting for fileservice layer to upload image with ProductId {}", productId);
            String imageName = fileService.uploadFile(image, imagePath);
            ProductDto product = productService.get(productId);
            product.setProductImageName(imageName);
            ProductDto productDto = productService.update(product, productId);
            ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("File Uploaded").success(true).status(HttpStatus.CREATED).build();
            log.info("Request Completed for fileservice layer to upload image with productId: {}", productId);
            return new ResponseEntity<>(imageResponse, HttpStatus.OK);
        }
    }

    /**
     * @author  Yogesh Shelar
     * @param productId
     * @param response
     * @throws IOException
     */
    //Serve User Image
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDto product = productService.get(productId);
        log.info("Product Image Name: {}", product.getProductImageName());
        InputStream resource = fileService.getResource(imagePath, product.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }

}

