[1mdiff --git a/src/main/java/Blackop778/MineCalc/common/Calculator.java b/src/main/java/Blackop778/MineCalc/common/Calculator.java[m
[1mindex 16ce9b2..2c483ab 100644[m
[1m--- a/src/main/java/Blackop778/MineCalc/common/Calculator.java[m
[1m+++ b/src/main/java/Blackop778/MineCalc/common/Calculator.java[m
[36m@@ -70,12 +70,21 @@[m [mpublic abstract class Calculator {[m
 	if (lastIndex != 0)[m
 	    math = math.substring(lastIndex + symbol.length(), math.length());[m
 	String[] maths = math.split(Pattern.quote(symbol));[m
[32m+[m	[32mfor (String s : maths) {[m
[32m+[m	[32m    for (Character c : s.toCharArray()) {[m
[32m+[m		[32misNumber(c, '7');[m
[32m+[m	[32m    }[m
[32m+[m	[32m}[m
 	System.out.println(maths);[m
     }[m
 [m
[31m-    public static boolean isNumber(Character current, char last) {[m
[31m-	if (current.toString().matches("//d/|.")) {[m
[31m-[m
[32m+[m[32m    public static boolean isNumber(Character current, Character last) {[m
[32m+[m	[32mif (current.toString().matches("//d|//.|[lpiLPI]")) {[m
[32m+[m	[32m    return true;[m
[32m+[m	[32m} else if (current.equals('-') && (!last.toString().matches("//d/|//./|[lpiLPI]") || !last.equals('/'))) {[m
[32m+[m	[32m    return true;[m
 	}[m
[32m+[m
[32m+[m	[32mreturn false;[m
     }[m
 }[m
