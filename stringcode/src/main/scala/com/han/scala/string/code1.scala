package com.han.scala.string

/**
  * @Title: ${file_name}
  * @Package ${package_name}
  * @Description: ${todo}
  * @author ceshi
  * @date 2019/10/12 0012下午 2:32
  **/
object code1 {

  def main(args: Array[String]): Unit = {

//    MoreLineString()
//    VarReplaceInString()
  allDealString()
  }


  def MoreLineString(): Unit ={

    val lineStr="""adfsadlkjsdlkajf
            |asdjglkjs
            |'sadgjlksadjlgjs'
            |"sadfjlksdjl"
      """.stripMargin('|')
    print(lineStr)
  }

  def VarReplaceInString(): Unit ={

    val name = "han"
    val age = 25

    print(s"$name is a ${age + 1} boy")
  }

  def allDealString(): Unit ={

    val s1 = "hello,word".filter(_!='l')
      .map(_.toUpper)

    val s2 = for(c <- "hello,world")yield c.toUpper

    println(s1)

    print(s2)
  }

}
