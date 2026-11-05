-- REVIEW REQUIRED. Prepared only; never executed by this integration.
-- This is a DDL proposal, NOT a read-only preflight script.
-- Execution requires the project owner's explicit authorization.
-- Read-only checks: ORACLE_VEHICLE_PREFLIGHT_READ_ONLY.sql and
-- ORACLE_VEHICLE_PREFLIGHT_ACCOUNTS_READ_ONLY.sql.
-- Current schema must resolve HY_CLB, HY_CLB_TEMP and their sequences.

-- NEW integration-owned audit/record-origin table. Create only after review.
-- No existing vehicle table or data is changed by this DDL.
CREATE TABLE VEHICLE_WORKFLOW_AUDIT (
    EVENT_ID VARCHAR2(36 CHAR) NOT NULL,
    TEMP_ID NUMBER(20,0) NOT NULL,
    VEHICLE_ID VARCHAR2(36 CHAR) NOT NULL,
    LETTER_GID VARCHAR2(36 CHAR) NOT NULL,
    ACTOR_ID VARCHAR2(36 CHAR) NOT NULL,
    ACTION VARCHAR2(30 CHAR) NOT NULL,
    FROM_STATE NUMBER(11,0),
    TO_STATE NUMBER(11,0) NOT NULL,
    EVENT_TIME TIMESTAMP NOT NULL,
    DETAIL VARCHAR2(1000 CHAR),
    ACCEPTED_VIN VARCHAR2(17 CHAR),
    CONSTRAINT PK_VEHICLE_WORKFLOW_AUDIT PRIMARY KEY (EVENT_ID),
    CONSTRAINT UK_VEHICLE_ACCEPTED_VIN UNIQUE (ACCEPTED_VIN)
);
CREATE INDEX IX_VEHICLE_AUDIT_TEMP ON VEHICLE_WORKFLOW_AUDIT(TEMP_ID, ACTION);
CREATE INDEX IX_VEHICLE_AUDIT_LETTER ON VEHICLE_WORKFLOW_AUDIT(LETTER_GID);
-- Prevent two CREATE markers for one temporary record without restricting other events.
CREATE UNIQUE INDEX UX_VEHICLE_AUDIT_CREATE ON VEHICLE_WORKFLOW_AUDIT
    (CASE WHEN ACTION = 'CREATE' THEN TEMP_ID ELSE NULL END);

-- No HY_CLB/HY_CLB_TEMP creation, column cloning, sequence reset, status mass update,
-- role grant, credential replacement or destructive statement is included here.
-- Use the existing administration UI to add the menu route below, bind an approved
-- applicant role to it, and retain separate pre-acceptance/acceptance reviewer roles:
-- /frontPage/vehicleWorkflow_index
-- The project owner approved reviewed manual inputs on 2026-11-03. Ordinary
-- formal approval requires a reviewer confirmation, already-issued permit number,
-- verified unexpired inspection date, and recorded inspection evidence.
-- Automated inspection/permit issuance remains unavailable.
