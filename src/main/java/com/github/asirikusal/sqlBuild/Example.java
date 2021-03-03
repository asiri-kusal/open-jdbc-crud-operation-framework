package com.github.asirikusal.sqlBuild;

import static com.github.asirikusal.sqlBuild.SqlBuilder.and;
import static com.github.asirikusal.sqlBuild.SqlBuilder.equal;
import static com.github.asirikusal.sqlBuild.SqlBuilder.notEqual;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Sort;

public class Example {

    public static void main(String[] args) {
//String select = "SELECT tutorial_id, tutorial_title, tutorial_author, submission_date FROM tutorials_tbl";
List<String> columns= Arrays.asList("tutorial_id","tutorial_title","tutorial_author","submission_date");
 ComplexTableDescription description = new ComplexTableDescription("tutorials_tbl",columns,"*","","");
  SqlBuilder sqlBuilder = new SqlBuilder(description);
  String sql = sqlBuilder.select("*")
                   .from("users")
                        .where(
                               and(
                                 equal("category", "active"),
        notEqual("name", "Joe")
              )
    )
    .groupBy("name", "address")
         .orderBy(
                new Sort(
                  new Sort.Order(Sort.Direction.ASC, "name"),
        new Sort.Order(Sort.Direction.DESC, "address")
              )
    )
    .limit(10, 50)
         .toSql();
    
//   Should produce the string:
//
//    SELECT  FROM users
//                      WHERE category = ? AND name != ?
//    GROUP BY name, address
//                       ORDER BY name ASC, address DESC
//                                                      LIMIT 10, 50

        System.out.println(sql);
   List<Object> parameters = sqlBuilder.getQueryParameterValues();
        System.out.println(parameters.toString());
//   Should return a list with { 'active', 'Joe' }
  

    }

}
