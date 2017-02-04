#查询价格变动数据
select *
from house.房屋交易信息 j
where j.房屋基本信息id in (
SELECT b.房屋基本信息id
FROM house.房屋交易信息 b
group by b.房屋基本信息id
having count(1) > 1
)