create database takeout;
use takeout;
/*创建客户表*/
CREATE TABLE customer(
    openid VARCHAR(255) NOT NULL PRIMARY KEY,
    nickName VARCHAR(20) NOT NULL ,
    phone VARCHAR(20) NOT NULL ,
    address VARCHAR(255) NOT NULL ,
    avatarUrl VARCHAR(255) NOT NULL,
    balance int NOT NULL,
    createTime Date,
    gender VARCHAR(4) NOT NULL ,
    sessionKey VARCHAR(255) NOT NULL,
    skey VARCHAR(255) NOT NULL,
    consumption Double 
  );

/*创建商家表*/
CREATE TABLE businesses(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(40) NOT NULL ,
  turnover DOUBLE(16,2) NOT NULL DEFAULT 0,
  phone VARCHAR(16) NOT NULL ,
  address VARCHAR(45) NOT NULL,
  password varchar(255) NOT NULL,
  password_salt varchar(255)
);
/*创建商品表*/
CREATE TABLE goods(
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(40) NOT NULL ,
  business_id INT NOT NULL ,
  price DOUBLE(16,2) NOT NULL ,
  limit_num INT ,
  FOREIGN KEY (business_id) REFERENCES businesses(id)
);
/*创建订单表*/
CREATE TABLE orders(
  id INT NOT NULL AUTO_INCREMENT,
  order_time DATETIME NOT NULL ,
  customer_id varchar(255) NOT NULL ,
  goods_id INT NOT NULL ,
  number INT NOT NULL ,
  total_cost DOUBLE(16,2) NOT NULL ,
  status INT NOT NULL DEFAULT 0 ,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES customer(openid),
  FOREIGN KEY (goods_id) REFERENCES goods(id)
);

/*创建触发器*/
DELIMITER #
/*限购以及计算订单总额 */
CREATE TRIGGER limit_buy
  BEFORE INSERT
  ON orders
  FOR EACH ROW
  BEGIN
    DECLARE p DOUBLE(16,2);  /*商品单价*/
    DECLARE lim INT;  /*限购数量*/
    SET p=(SELECT price FROM goods WHERE goods.id=NEW.goods_id);
    SET lim=(SELECT limit_num FROM goods WHERE goods.id=NEW.goods_id);
    if (lim IS NOT NULL AND NEW.number>lim) THEN
      SET NEW.number=lim;
    END IF ;
    SET NEW.total_cost=NEW.number*p;
  END #
 /*更新客户消费总额 */
 CREATE TRIGGER calculate_consumption
  AFTER INSERT
  ON orders
  FOR EACH ROW
  BEGIN
    UPDATE customer
      SET consumption=consumption+NEW.total_cost
    WHERE customer.openid=NEW.customer_id;
  END #
/*更新商家总营业额以及退款 */
CREATE TRIGGER calculate_turnover
  BEFORE UPDATE
  ON orders
  FOR EACH ROW
  BEGIN
    IF (NEW.state=1 AND OLD.state=0) THEN
      UPDATE businesses
        SET turnover=turnover+OLD.total_cost
      WHERE id=(SELECT business_id FROM goods WHERE goods.id=NEW.goods_id);
    ELSEIF (NEW.state=3 AND OLD.state=0) THEN
      UPDATE customer
      SET consumption=consumption-OLD.total_cost
      WHERE openid=OLD.customer_id;
    ELSEIF (NEW.state<OLD.state) THEN
      SET NEW.state=OLD.state;
    END IF ;
  END #
DELIMITER ;

/*模拟数据*/
/*客户注册*/
INSERT INTO customers(name, phone, address,password)
VALUES ('李白','18512312300','华东交通大学42公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('杜甫','18612312301','华东交通大学43公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('马云云','18712312302','华东交通大学37公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('马花腾','18712412303','江西理工大学11公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('孙悟空','18572352304','江西财经大学32公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('唐僧','18632314305','江西财经大学9公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('武则天','18712312406','江西财经大学21公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('独孤求败','18519312307','江西理工大学13公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('展昭','18112332808','江西理工大学25公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('赵灵儿','18112812309','华东交通大学8公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('东方不败','18152862310','江西财经大学34公寓',"a");
INSERT INTO customers(name, phone, address,password)
VALUES ('貂蝉','18352388511','江西理工大学24公寓',"a");
/*商家注册*/
INSERT INTO businesses(name, phone, address,password)
VALUES ('回家吃饭','400123100','南昌市双港东大街333号',"a");
INSERT INTO businesses(name, phone, address,password)
VALUES ('佳佳饺子馆','400123101','南昌市双港东大街531号',"a");
INSERT INTO businesses(name, phone, address,password)
VALUES ('港式脆排饭','400123102','南昌市邹家自然村交通学院旁',"a");
INSERT INTO businesses(name, phone, address,password)
VALUES ('膳当家','400123103','南昌市华东交通大学',"a");
INSERT INTO businesses(name, phone, address,password)
VALUES ('沙县小吃','400123104','南昌市双港东大街立通大厦旁',"a");
/*商品录入*/
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('重庆鸡公煲',1,13.9,1);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('麻辣香锅',1,12.5,5);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('土豆烧牛腩',1,14.9,NULL);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('韭菜猪肉馅',2,14.9,3);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('香菇猪肉馅',2,14.9,3);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('玉米猪肉馅',2,15.9,3);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('黑椒脆排饭',3,9.9,NULL);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('孜然脆排饭',3,9.5,NULL);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('叉烧脆排饭',3,11.8,2);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('腐竹黄焖鸡',4,10.8,3);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('黄焖鸡小份微辣',4,13,5);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('黄焖鸡小份中辣',4,14,5);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('鲜肉蒸饺',5,4,6);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('香拌混沌',5,5,3);
INSERT INTO goods(name, business_id, price, limit_num)
VALUES ('蛋炒南昌米粉',5,7,NULL);
/*客户提交订单*/
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2017-12-8 12:14:35',2,3,2);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2017-12-8 11:45:12',4,5,5);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2017-12-9 18:14:35',5,13,7);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2017-12-9 19:44:35',9,12,1);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2017-12-10 11:55:34',7,14,6);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-3 12:04:42',1,7,9);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-5 13:11:56',3,4,1);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-11 18:09:27',9,8,1);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-12 13:11:56',11,8,4);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-15 19:28:49',5,14,3);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-16 13:08:40',7,1,2);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-17 17:30:29',9,4,5);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-19 12:00:26',12,9,2);
INSERT orders(order_time, customer_id, goods_id, number)
VALUES ('2018-1-21 17:34:56',4,14,4);