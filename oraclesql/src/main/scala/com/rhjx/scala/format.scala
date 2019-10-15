package com.rhjx.scala

/**
  * @Title: ${file_name}
  * @Package ${package_name}
  * @Description: ${todo}
  * @author ceshi
  * @date 2019/10/15 0015下午 3:21
  **/
object format {

  /**
    * 获得oracle中的表名和字段信息
    *
    */

  def getOracleInfo(oracleSql : String) ={

    //获得正则对象
    val blank = "\\s+".r.replaceAllIn(oracleSql," ")
    //获得表名对象
    val tableName = oracleSql.split(" ").apply(2).split('(').apply(0)
    //    val code = "(.*)/".r.findFirstIn(blank)

    //获得字段信息
    val code = blank.split("[(]",2)(1)
    val valueCode = code.substring(0,code.length - 1)
    //    print(valueCode)
   (tableName,valueCode)
  }

  def dataxField(oracleSql : String)={


    //表名
    val tableName = getOracleInfo(oracleSql)._1

    //表字段
    val filedCodes = getOracleInfo(oracleSql)._2

    //类型转换

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


    //转换后的类型信息
    val longStr = longRegex.replaceAllIn(filedCodes, "long")
    val stringStr = stringRegex.replaceAllIn(longStr, "string")
    val dateStr = dateRegex.replaceAllIn(stringStr, "date")
    val doubleStr = doubleRegex.replaceAllIn(dateStr, "double")
    val booleanStr = booleanRegex.replaceAllIn(doubleStr, "boolean")
    val valueCode = bytesRegex.replaceAllIn(booleanStr, "bytes")

    val finalFieldCode = valueCode.split(",").map(_.trim )

    finalFieldCode

  }

  def getJson(oracleSql : String,primaryKey : String*): Unit ={

    val fieldCode = dataxField(oracleSql)

    fieldCode.

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

    print(dataxJson(s))
//    dataxJson(s)
  }


}
