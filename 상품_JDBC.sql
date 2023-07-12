DROP TABLE PRODUCT;

CREATE TABLE PRODUCT (
    PRODUCT_ID VARCHAR2(20) PRIMARY KEY,    -- ��ǰ���̵�
    P_NAME VARCHAR2(20) NOT NULL,           -- ��ǰ��
    PRICE NUMBER NOT NULL,                  -- ��ǰ����
    DESCRIPTION VARCHAR2(20),               -- ��ǰ������
    STOCK NUMBER NOT NULL                   -- ���
);

SELECT * FROM PRODUCT;

INSERT INTO PRODUCT VALUES('nb_ss7', '�Ｚ��Ʈ��', 1570000, '�ø���7', 10);
INSERT INTO PRODUCT VALUES('nb_ama4', '�ƺϿ���', 1200000, 'xcode4', 20);
INSERT INTO PRODUCT VALUES('pc_ibm', 'ibmPC', 750000, 'window8', 5);

commit;