import kotlin.random.Random

// Define the node
abstract class List
object  Nil:List()
data class Node(val head:Int, val tail:List):List()

// Define Alias para função de int para int
typealias Fint = (Int)->Int

// =============================================
// Implementa função map para o tipo da List
// =============================================
fun map(f:Fint, l:List):List = when(l){
    is Node -> Node(f(l.head), map(f, l.tail))
    else -> Nil
}

val increment = {l:List -> map({x:Int -> x+1}, l)}
val decrement = {l:List -> map({x:Int -> x-1}, l)}
val duplicate = {l:List -> map({x:Int -> x*2}, l)}

// =============================================
// Implementa função filter para o tipo da List
// =============================================
fun filter(f:(Int) -> Boolean, l: List):List = when{
    l is Node && f(l.head) -> Node(l.head, filter(f, l.tail))
    l is Node && !f(l.head) -> filter(f, l.tail)
    else -> Nil
}

val selectEven = {l:List -> filter({x:Int -> x%2 == 0}, l)}
val selectOdd = {l:List -> filter({x:Int -> x%2 != 0}, l)}
val greaterThan10 = {l:List -> filter({x:Int -> x > 10}, l)}
val greaterThanEqual5 = {l:List -> filter({x:Int -> x >= 5}, l)}
val multipleOf3 = {l:List -> filter({x:Int -> x%3 == 0}, l)}
val notMultipleOf3 = {l:List -> filter({x:Int -> x%3 != 0}, l)}


// =============================================
// Implementa função reduce para o tipo da List
// =============================================

fun reduce(f:(Int,Int) -> Int, l:List, v0:Int):Int = when(l){
    is Node -> l.head + reduce(f, l.tail, v0)
    else -> v0
}

val sum = {l:List -> reduce({x,y -> x + y}, l, 0)}
val multiply = {l:List -> reduce({x,y -> x * y}, l, 1)}
val divide = {l:List -> reduce ({x,y -> x / y}, l, 1)}

// =============================================
// Gera listas
// =============================================
fun generateList(size:Int):List = when{
    size > 0 -> Node(size, generateList(size-1))
    else -> Nil
}

fun generateRandomList(size:Int, min:Int, max:Int):List = when{
    size > 0 -> Node(randomNumber(min, max), generateRandomList(size-1, min, max))
    else -> Nil
}

fun randomNumber(a:Int, b:Int):Int { return Random.nextInt(a, b) }

// =============================================
// Implementacao dos exercicios de revisao
// =============================================

/*
 1. Escreva uma função que receba duas funções sobre
 inteiros (f e g) e retorne um função que devolva,
 para cada ponto,o maior valor entre elas. Ou seja:

 maior(f,g)(x) = maior(f(x), g(x))
 */

fun maior(f:Fint, g:Fint):Fint = {
    x:Int -> if(f(x) > g(x))
    f(x)
    else
    g(x)
}

/*
2. Escreva a função potencia definida como:
potencia(f,0)(x) = x
potencia(f,1)(x) = f(x)
potencia(f,2)(x) = f(f(x))
 */

/*
fun potencia(f:Fint, n:Int):Fint =
    {x ->
        when (n) {
            0 -> 1
            1 -> f(x)
            else -> f(potencia(f,n-1))
        }
    }
*/

/*
3 - Escreva uma função de encadeamento definida como:
cadeia(f,g)(x) = f(g(x))
 */

fun cadeia(f:Fint, g:Fint):Fint = {
    x -> f(g(x))
}


fun main(){
    val l1 = generateList(10)
    println(l1)
    val l2 = increment(l1)
    println(l2)
    println(decrement(l2))
    val l3 = selectEven(l2)
    println(l3)
    println(selectOdd(l2))
    val l4 = generateRandomList(10, 0,30)
    println(greaterThan10(l4))
    val l5 = generateRandomList(5, 1, 10)
    println(sum(l5))
    println(multiply(l5))
    println(divide(l5))

    /*
    1 - Escreva uma expressão em kotlin para calcular os 10
    primeiros números inteiros a partir de 5 que não
    forem muiltiplos de 3
     */
    println(greaterThanEqual5(notMultipleOf3(l4)))

    /*
        No modelo abaixo, a execução segue o caminho sequencial (Eager), sendo cada
        bloco de código executado no momento da criação da variável.
        A saída esperada para essa execução é:

        a
        b
        c
        5
     */
    val av1 = run {
        println("a")
        2
    }
    val av2 = run {
        println("b")
        3
    }
    println("c")
    println(av1 + av2)

    /*
        No modelo abaixo, a execução segue o caminho de Avaliação Lazy, sendo cada
        bloco de código executado no momento da utilização da variável.
        A saída esperada para essa execução é:

        c
        a
        b
        5
     */
    val bv1 by lazy {
        println("a")
        2
    }
    val bv2 by lazy {
        println("b")
        3
    }
    println("c")
    println(bv1 + bv2)
}