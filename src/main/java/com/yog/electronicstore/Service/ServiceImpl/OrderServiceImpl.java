package com.yog.electronicstore.Service.ServiceImpl;
/**
 package com.rinku.electronic.store.ElectronicStore.service.serviceImpl;

 import com.rinku.electronic.store.ElectronicStore.dtos.CreateOrderRequest;
 import com.rinku.electronic.store.ElectronicStore.dtos.OrderDto;
 import com.rinku.electronic.store.ElectronicStore.dtos.PageableResponse;
 import com.rinku.electronic.store.ElectronicStore.entity.Cart;
 import com.rinku.electronic.store.ElectronicStore.entity.CartItem;
 import com.rinku.electronic.store.ElectronicStore.entity.Order;
 import com.rinku.electronic.store.ElectronicStore.entity.User;
 import com.rinku.electronic.store.ElectronicStore.exception.ResourceNotFoundException;
 import com.rinku.electronic.store.ElectronicStore.helper.ApiConstants;
 import com.rinku.electronic.store.ElectronicStore.helper.AppConstants;
 import com.rinku.electronic.store.ElectronicStore.repository.CartRepo;
 import com.rinku.electronic.store.ElectronicStore.repository.OrderRepo;
 import com.rinku.electronic.store.ElectronicStore.repository.UserRepo;
 import com.rinku.electronic.store.ElectronicStore.service.OrderService;
 import lombok.extern.slf4j.Slf4j;
 import org.modelmapper.ModelMapper;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.*;
 import org.springframework.data.repository.query.FluentQuery;
 import org.springframework.stereotype.Service;

 import java.awt.geom.GeneralPath;
 import java.util.Date;
 import java.util.List;
 import java.util.Optional;
 import java.util.UUID;
 import java.util.concurrent.atomic.AtomicReference;
 import java.util.function.Function;
 import java.util.stream.Collectors;


 @Service
 @Slf4j
 public class OrderServiceImpl implements OrderService {
 @Autowired
 private UserRepo userRepo;
 @Autowired
 private OrderRepo orderRepo;
 @Autowired
 private CartRepo cartRepo;

 @Autowired
 private ModelMapper mapper;

 @Override
 public OrderDto createOrder(CreateOrderRequest orderDto) {
 log.info("Request Starting  to create order ");
 String userId = orderDto.getUserId();
 String cartId = orderDto.getCartId();
 //user fetch
 log.info("Request Starting  to find the user  with userId : {}", userId);
 User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.EXCEPTION_MSG));
 //cart fetch
 log.info(" Request Starting  to find the cart with cartId : {}", cartId);
 Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CART_NOT_FOUND));
 List<CartItem> cartItem = cart.getCartItem();
 if(cartItem.size()<=0){
 throw new BadRequestApiException(AppConstant.CART_INVALID_ITEMS);
 }
 //other checks
 Order order = Order.builder()
 .billingName(orderDto.getBillingName())
 .billingAddress(orderDto.getBillingAddress())
 .billingPhone(orderDto.getBillingPhone())
 .orderDate(new Date())
 .delieveryDate(null)
 .orderStatus(orderDto.getOrderStatus())
 .orderId(UUID.randomUUID().toString())
 .paymentStatus(orderDto.getPaymentStatus())
 .user(user).build();

 //order Items ,amount
 AtomicReference<Integer> atomicReference = new AtomicReference<Integer>(0);
 List<OrderItem> orderItems = cartItem.stream().map(cartItems -> {
 //CartItem -> OrderItem
 OrderItem orderItm= OrderItem.builder()
 .quantity(cartItems.getQuantity())
 .product(cartItems.getProduct())
 .totalPrize((int) (cartItems.getCartItemId()*cartItems.getProduct().getDiscountPrice()))
 .order(order)
 .build();
 atomicReference.set(atomicReference.get()+orderItm.getTotalPrize());
 return new OrderItem();
 }).collect(Collectors.toList());
 order.setItems(orderItems);
 order.setOrderAmount(atomicReference.get());

 cart.getCartItem().clear();
 cartRepo.save(cart);
 Order save = orderRepo.save(order);
 log.info("Request completed  to create the order ");
 return mapper.map(save,OrderDto.class);
 }

 @Override
 public void removeOrder(String orderId) {
 log.info(" Request Starting  to remove the order with orderId : {}", orderId);
 Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.ORDER_NOT_FOUND));
 orderRepo.delete(order);
 log.info(" Request completed  to remove the order with orderId : {}", orderId);
 }

 @Override
 public List<OrderDto> getAllOrdersOfUser(String userId) {
 log.info("Request Starting  to get All Orders of  user  with userId : {}", userId);
 //user fetch
 User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstant.EXCEPTION_MSG));
 List<Order> orders = orderRepo.findByUser(user);
 List<OrderDto> orderList = orders.stream().map((order) -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
 log.info("Request completed  to get All Orders of  user  with userId : {}", userId);
 return orderList;
 }

 @Override
 public PageableResponse<OrderDto> getAllOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
 log.info("Request Starting  to get All Orders ");
 Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
 Pageable pageable =PageRequest.of(pageNumber,pageSize,sort);
 Page<Order> page = orderRepo.findAll(pageable);
 log.info("Request completed  to get All Orders ");
 return General.getPageableResponse(page,OrderDto.class);
 }

 @Override
 public List<Order> findAll() {
 return null;
 }

 @Override
 public List<Order> findAll(Sort sort) {
 return null;
 }

 @Override
 public Page<Order> findAll(Pageable pageable) {
 return null;
 }

 @Override
 public List<Order> findAllById(Iterable<String> strings) {
 return null;
 }

 @Override
 public long count() {
 return 0;
 }

 @Override
 public void deleteById(String s) {

 }

 @Override
 public void delete(Order entity) {

 }

 @Override
 public void deleteAllById(Iterable<? extends String> strings) {

 }

 @Override
 public void deleteAll(Iterable<? extends Order> entities) {

 }

 @Override
 public void deleteAll() {

 }

 @Override
 public <S extends Order> S save(S entity) {
 return null;
 }

 @Override
 public <S extends Order> List<S> saveAll(Iterable<S> entities) {
 return null;
 }

 @Override
 public Optional<Order> findById(String s) {
 return Optional.empty();
 }

 @Override
 public boolean existsById(String s) {
 return false;
 }

 @Override
 public void flush() {

 }

 @Override
 public <S extends Order> S saveAndFlush(S entity) {
 return null;
 }

 @Override
 public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
 return null;
 }

 @Override
 public void deleteAllInBatch(Iterable<Order> entities) {

 }

 @Override
 public void deleteAllByIdInBatch(Iterable<String> strings) {

 }

 @Override
 public void deleteAllInBatch() {

 }

 @Override
 public Order getOne(String s) {
 return null;
 }

 @Override
 public Order getById(String s) {
 return null;
 }

 @Override
 public Order getReferenceById(String s) {
 return null;
 }

 @Override
 public <S extends Order> Optional<S> findOne(Example<S> example) {
 return Optional.empty();
 }

 @Override
 public <S extends Order> List<S> findAll(Example<S> example) {
 return null;
 }

 @Override
 public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
 return null;
 }

 @Override
 public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
 return null;
 }

 @Override
 public <S extends Order> long count(Example<S> example) {
 return 0;
 }

 @Override
 public <S extends Order> boolean exists(Example<S> example) {
 return false;
 }

 @Override
 public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
 return null;
 }
 }
 **/
