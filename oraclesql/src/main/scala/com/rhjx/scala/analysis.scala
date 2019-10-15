package com.rhjx.scala

import com.alibaba.fastjson.JSONObject

import scala.collection.mutable
import scala.util.parsing.json.JSON

/**
  * @Title: ${file_name}
  * @Package ${package_name}
  * @Description: ${todo}
  * @author ceshi
  * @date 2019/10/14 0014下午 5:48
  **/
object analysis {

  /**
    * 获得oracle中的表名和字段信息
    *
    */

  def getOracleInfo(oracleSql : String) : Map[String,String]={

    //获得正则对象
    val blank = "\\s+".r.replaceAllIn(oracleSql," ")
    //获得表名对象
    val tableName = oracleSql.split(" ").apply(2).split('(').apply(0)
//    val code = "(.*)/".r.findFirstIn(blank)

    //获得字段信息
    val code = blank.split("[(]",2)(1)
      val valueCode = code.substring(0,code.length - 1)
//    print(valueCode)
    Map("tableName"->tableName,"valueCode"->valueCode)
  }

  /**
    * splitPK :String ,jdbcURL : String ,username : String,password : String,
    * flushBatch : Int,kuduTableName : String,isUpsert : Boolean,primaryKey : String*
    *
    * @param oracleSql
    */

  def dataxJson(oracleSql : String,primaryKey : String*): Unit = {

    //获得oracle中信息
    val inforTuple = getOracleInfo(oracleSql)

    //获得表名
//    val tableName: String = inforTuple.getOrElse(tableName, "0")
//
//    if (tableName == "0") {
//      print("表名解析错误")
//    }
    //    println(inforTuple._2)
    //long类型的Regex对象
    val longRegex = "NUMBER[(]\\d+[)]".r
    //string类型的Regex对象
    val stringRegex = "VARCHAR2[(]\\d+[)]|CHAR[(]1[)]".r
    //date类型的Regex对象
    val dateRegex = "TIMESTAMP|DATE".r
    //double类型的Regex对象
    val doubleRegex = "NUMBER[(]\\d+,\\d+[)]|DECIMAL[(]\\d+,\\d+[)]|FLOAT[(]\\d+,\\d+[)]".r
    //boolean类型的Regex对象
    val booleanRegex = "bit|boolean".r
    //bytes类型的Tegex对象
    val bytesRegex = "BLOB|BFILE|RAW|LONG RAW".r

    //获得字段信息
    val valueCodes = inforTuple.getOrElse("valueCode", "0")

    //类型转换
    val longStr = longRegex.replaceAllIn(valueCodes, "long")
    val stringStr = stringRegex.replaceAllIn(longStr, "string")
    val dateStr = dateRegex.replaceAllIn(stringStr, "date")
    val doubleStr = doubleRegex.replaceAllIn(dateStr, "double")
    val booleanStr = booleanRegex.replaceAllIn(doubleStr, "boolean")
    val valueCode = bytesRegex.replaceAllIn(booleanStr, "bytes")

    val fieldCode = Map()
    //获得k-v形式的字段类型
    val tuples = valueCode.split(",").map(_.trim).map(field => Map(field.split(" ")(0).trim -> field.split(",")(1).trim))

    //装换为Map结构
    val mapTuples = tuples.fold(Map()) { (m1, m2) => m1 ++ m2 }

    val i = 0;
    //主键的json格式转换
    val primaryKeySeq = primaryKey.map(key => (i+1 ,key, mapTuples.get(key)))

    print(primaryKeySeq)



//    val jsonStr = s"""{
//                    |    "job": {
//                    |        "content": [
//                    |            {
//                    |                "reader": {
//                    |                    "name": "oraclereader",
//                    |                    "parameter": {
//                    |                        "splitPk": "$splitPK",
//                    |                        "connection": [
//                    |                            {
//                    |                                "jdbcUrl": ["$jdbcURL"],
//                    |                                "querySql":["select $codes where init_date=20190801"]
//                    |                            }
//                    |                        ],
//                    |                        "password": "$password",
//                    |                        "username": "$username"
//                    |                    }
//                    |                },
//                    |                "writer": {
//                    |                    "name": "kuduwriter1",
//                    |                    "parameter": {
//                    |                        "flushBatch": $flushBatch,
//                    |                        "column": [
//                    |                    $codesJson],
//                    |                        "isUpsert": $isUpsert,
//                    |                        "master": "192.168.10.179,192.168.10.180,192.168.10.181",
//                    |                        "mutationBufferSpace": 3000,
//                    |                    "primaryKey": [$primaryKey],
//                    |                        "table": "$kuduTableName"
//                    |                    }
//                    |                }
//                    |            }
//                    |        ],
//                    |        "setting": {
//                    |            "speed": {
//                    |                "channel": 2,
//                    |            }
//                    |                }
//                    |    }
//                    |}
//                    |"""

  }
  def main(args: Array[String]): Unit = {

    val s = "create table HS_HIS.HIS_ASSET" +
      "(init_date               NUMBER(10),  " +
      "  branch_no               NUMBER(10),  " +
      "  fund_account            VARCHAR2(18),  " +
      "  money_type              VARCHAR2(3), " +
      "  fund_asset              NUMBER(19,2),  " +
      "  secu_market_value       NUMBER(19,2)," +
      "  opfund_market_value     NUMBER(19,2)," +
      " total_asset             NUMBER(19,2)," +
      "  prod_market_value       NUMBER(19,2)," +
      "  opt_market_value        NUMBER(19,2)," +
      "  ofcash_market_value     NUMBER(19,2)," +
      "  pfund_market_value      NUMBER(19,2)," +
      " hkfund_market_value     NUMBER(19,2)," +
      " secum_market_value      NUMBER(19,2)," +
      "  prod_secu_market_value  NUMBER(19,2)," +
      "  client_id               VARCHAR2(18)," +
      " asset_prop              CHAR(1),  " +
      "  limit_secu_market_value NUMBER(19,2))"
//    getOracleInfo(s)
    dataxJson(s,"init_date","client_id","asset_prop")
  }

}
