-- trovare tutti i super eroi della marvel con i loro nomi originali
-- e la lista di super poteri
select su.superhero_name,
       su.full_name,
       p.publisher_name,
       GROUP_CONCAT(DISTINCT  s.power_name
                    ORDER BY  s.power_name ASC SEPARATOR ',')
from superhero su
         inner join publisher p on p.id=su.publisher_id
         inner join hero_power hp on su.id = hp.hero_id
         inner join superpower s on hp.power_id = s.id
-- where  publisher_name = 'Marvel Comics'
group by su.superhero_name, su.full_name, p.publisher_name
order by  publisher_name,superhero_name;

