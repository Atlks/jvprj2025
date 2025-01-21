
函数版本全局管理，可能跨语言函数管理。。


= java
C:\Users\attil\IdeaProjects\jvprj2025\src\util\Fltr.java


public class Fltr {

    /**
     * //过滤数组，根据指定的条件whereFun
     *
     * @param list
     * @param whereFun
     * @return
     */
    public static List<SortedMap<String, String>> fltr2501(
            List<SortedMap<String, String>> list,
            Predicate<SortedMap<String, String>> whereFun) {


= c#


C:\0prj\imbotTg\Wbsvr\lib\arrCls.cs
//过滤数组，根据指定的条件whereFun
public static List<SortedList> ArrFltr(List<SortedList> rows, Func<SortedList, bool> whereFun)
{