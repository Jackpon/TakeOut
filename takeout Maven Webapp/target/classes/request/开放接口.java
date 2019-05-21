/**接口数据所需参数
 * 注册customer：name, phone, address,password
 * 注册Business：name, phone, address,password
 * Business录入商品：name, business_id, price, limit_num(限购数量) img
 * customer提交订单：order_time, customer_id, goods_id, number
 * 改变订单状态：提供订单id
 */

/**
 * 订单状态
 * 0：用户下单但还没被商家接单
 * 1：商家接单
 * 2：用户确认到货
 * 3：取消订单
 */


//待解决：密码加密，图片上传