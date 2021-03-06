
CREATE TABLE CUST_DETAILS
(
CUSTID VARCHAR(12) NOT NULL PRIMARY KEY,
BILL_CYCLE VARCHAR(2) NOT NULL,
EMAIL VARCHAR(128) NOT NULL,
TN NUMERIC(10) NOT NULL
);

CREATE INDEX CUST_DETAILS_IX1
 ON CUST_DETAILS (CUSTID);
 
CREATE TABLE USAGE_INFO
(
CUSTID VARCHAR(12) NOT NULL ,
HR_0 NUMERIC(12) NOT NULL DEFAULT 0,
HR_1 NUMERIC(12) NOT NULL DEFAULT 0,
HR_2 NUMERIC(12) NOT NULL DEFAULT 0,
HR_3 NUMERIC(12) NOT NULL DEFAULT 0,
HR_4 NUMERIC(12) NOT NULL DEFAULT 0,
HR_5 NUMERIC(12) NOT NULL DEFAULT 0,
HR_6 NUMERIC(12) NOT NULL DEFAULT 0,
HR_7 NUMERIC(12) NOT NULL DEFAULT 0,
HR_8 NUMERIC(12) NOT NULL DEFAULT 0,
HR_9 NUMERIC(12) NOT NULL DEFAULT 0,
HR_10 NUMERIC(12) NOT NULL DEFAULT 0,
HR_11 NUMERIC(12) NOT NULL DEFAULT 0,
HR_12 NUMERIC(12) NOT NULL DEFAULT 0,
HR_13 NUMERIC(12) NOT NULL DEFAULT 0,
HR_14 NUMERIC(12) NOT NULL DEFAULT 0,
HR_15 NUMERIC(12) NOT NULL DEFAULT 0,
HR_16 NUMERIC(12) NOT NULL DEFAULT 0,
HR_17 NUMERIC(12) NOT NULL DEFAULT 0,
HR_18 NUMERIC(12) NOT NULL DEFAULT 0,
HR_19 NUMERIC(12) NOT NULL DEFAULT 0,
HR_20 NUMERIC(12) NOT NULL DEFAULT 0,
HR_21 NUMERIC(12) NOT NULL DEFAULT 0,
HR_22 NUMERIC(12) NOT NULL DEFAULT 0,
HR_23 NUMERIC(12) NOT NULL DEFAULT 0,
DAY_0 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_1 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_2 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_3 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_4 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_5 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_6 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_7 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_8 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_9 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_10 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_11 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_12 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_13 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_14 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_15 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_16 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_17 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_18 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_19 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_20 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_21 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_22 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_23 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_24 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_25 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_26 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_27 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_28 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_29 NUMERIC(14) NOT NULL DEFAULT 0,
DAY_30 NUMERIC(14) NOT NULL DEFAULT 0,
MONTH_0 NUMERIC(16) NOT NULL DEFAULT 0,
LAST_USAGE_UPDATE_TIME TIMESTAMP 
);
ALTER TABLE USAGE_INFO
 ADD CONSTRAINT fk_USAGE_INFO
 FOREIGN KEY (CUSTID)
 REFERENCES CUST_DETAILS(CUSTID);

CREATE INDEX USAGE_INFO_IX1
 ON USAGE_INFO (CUSTID);


CREATE TABLE MONTH_USAGE_HISTORY
(
CUSTID VARCHAR(12) NOT NULL ,
MONTHLY_USAGE NUMERIC(16) NOT NULL DEFAULT 0,
BILLED_DATE TIMESTAMP 
);
CREATE INDEX MONTH_USAGE_HISTORY_IX1
 ON MONTH_USAGE_HISTORY (CUSTID);

ALTER TABLE MONTH_USAGE_HISTORY
 ADD CONSTRAINT fk_MONTH_USAGE_HISTORY
 FOREIGN KEY (CUSTID)
 REFERENCES CUST_DETAILS(CUSTID);

CREATE TABLE EXCLUDE_USAGE_FILTERS
(
CUSTID VARCHAR(12) NOT NULL ,
WEBSITE VARCHAR(256) NOT NULL,
START_TIME_HR NUMERIC(2) NOT NULL,
START_TIME_MIN NUMERIC(2) NOT NULL,
END_TIME_HR NUMERIC(2) NOT NULL,
END_TIME_MIN NUMERIC(2) NOT NULL
);


INSERT INTO CUST_DETAILS VALUES ('123456','01','akhila.kotcherlakota@verizon.com','9790887567');
INSERT INTO CUST_DETAILS VALUES ('1234564','01','ramanathan.hariharan@verizon.com','9790887567');
INSERT INTO CUST_DETAILS VALUES ('234561','01','mohan.purushothaman@verizon.com','9790887567');


INSERT INTO USAGE_INFO(CUSTID) VALUES('123456');
INSERT INTO USAGE_INFO(CUSTID) VALUES('1234564');
INSERT INTO USAGE_INFO(CUSTID) VALUES('234561');

select * from USAGE_INFO;