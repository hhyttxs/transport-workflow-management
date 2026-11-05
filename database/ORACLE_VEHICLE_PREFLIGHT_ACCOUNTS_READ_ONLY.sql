-- READ-ONLY account/menu/ownership checks. No grants or user changes.
-- Supply existing approved account codes as SQL Developer bind variables:
-- :applicant_code, :preaccept_code, :accept_code. Never supply passwords here.
WITH actors AS (
 SELECT 'APPLICANT' actor_type,:applicant_code account_code FROM DUAL
 UNION ALL SELECT 'PRE_ACCEPT',:preaccept_code FROM DUAL
 UNION ALL SELECT 'ACCEPT',:accept_code FROM DUAL
)
SELECT a.actor_type,u.CODE,u.GID,u.STATUS,u.XZQH_CODE,u.XZJG_CODE,
 r.GID role_gid,r.STATUS role_status,
 CASE WHEN EXISTS (
  SELECT 1 FROM TAB_CDGL g JOIN TAB_MENU m ON m.CODE=g.MENU_CODE
  WHERE g.YHCD_GID=r.GID AND m.STATUS='0' AND (
   (a.actor_type='APPLICANT' AND m.URL='/frontPage/vehicleWorkflow_index') OR
   (a.actor_type='PRE_ACCEPT' AND m.URL IN ('/frontPage/servicePreAccept_index','/roadFreight/servicePreAccept_index')) OR
   (a.actor_type='ACCEPT' AND m.URL IN ('/frontPage/serviceAccept_index','/roadFreight/serviceAccept_index'))
  )) THEN 'GRANTED' ELSE 'MISSING' END menu_evidence
FROM actors a LEFT JOIN TAB_YHWH u ON u.CODE=a.account_code
LEFT JOIN TAB_YHCD r ON r.GID=u.YHCD_GID ORDER BY a.actor_type,u.GID;
SELECT COUNT(*) owned_licensed_enterprises FROM HY_YHXX e
JOIN TAB_YHWH u ON u.GID=e.CREATOR
WHERE u.CODE=:applicant_code AND u.STATUS=1 AND e.XKZH IS NOT NULL;
SELECT CODE,URL,STATUS FROM TAB_MENU
WHERE URL IN ('/frontPage/vehicleWorkflow_index','/frontPage/servicePreAccept_index',
 '/roadFreight/servicePreAccept_index','/frontPage/serviceAccept_index','/roadFreight/serviceAccept_index')
ORDER BY URL,CODE;
