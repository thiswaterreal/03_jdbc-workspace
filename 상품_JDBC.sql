DROP TABLE PRODUCT;

CREATE TABLE PRODUCT (
    PRODUCT_ID VARCHAR2(20) PRIMARY KEY,    -- 상품아이디
    P_NAME VARCHAR2(20) NOT NULL,           -- 상품명
    PRICE NUMBER NOT NULL,                  -- 상품가격
    DESCRIPTION VARCHAR2(20),               -- 상품상세정보
    STOCK NUMBER NOT NULL                   -- 재고
);

SELECT * FROM PRODUCT;

INSERT INTO PRODUCT VALUES('nb_ss7', '삼성노트북', 1570000, '시리즈7', 10);
INSERT INTO PRODUCT VALUES('nb_ama4', '맥북에어', 1200000, 'xcode4', 20);
INSERT INTO PRODUCT VALUES('pc_ibm', 'ibmPC', 750000, 'window8', 5);

commit;