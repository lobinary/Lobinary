select job_name,	begincolumn,	endcolumn,	beginline,	endline,	priority,	rule,	ruleset,	git_revision,	src_file,	content,	rule_desc,	rule_example,	search_time
from
(
SELECT
	job_name,	begincolumn,	endcolumn,	beginline,	endline,	priority,	rule,	ruleset,	git_revision,	src_file,	content,	rule_desc,	rule_example,	search_time
FROM
	ios_sonar_show_bugs a
WHERE
	EXISTS (
		SELECT
			1
		FROM
			ios_sonar_show_bugs b
		WHERE
			job_name = 'jzyhcp::DJOY_jzapp_ios-58-jz-app_1-1-0_sonartest'
		GROUP BY
			src_file
		HAVING
			a.src_file = b.src_file
		AND max(search_time) = a.search_time
	)
AND priority LIKE '%%'
AND search_time > '1970-01-01 00:00:00'
AND search_time < '2018-06-28 17:26:07'
AND src_file LIKE '%%'
) x
,ios_sonar_show_oclintrules c
where x.rule = c.rule_name