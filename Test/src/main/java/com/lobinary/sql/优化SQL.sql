SELECT TAB_NAME 
FROM TABLES 
WHERE (TAB_NAME,DB_VER) = (
 SELECT TAB_NAME,DB_VER 
 FROM TAB_COLUMNS 
 WHERE VERSION = 604
 )