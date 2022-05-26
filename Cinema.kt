package cinema

import java.math.BigDecimal
import java.math.RoundingMode

var tenDollarTickets = 0
var eightDollarTickets = 0


fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()

    println("Enter the number of seats in each row:")
    val seats = readln().toInt()

    val cinema = MutableList(rows){ MutableList(seats){"S"} }

    do {
        printMenu()
        val input = readln().toInt()
        when (input) {
            1 -> showSeats(cinema)
            2 -> buyATicket(cinema, rows, seats)
            3 -> statistics(rows, seats)
        }
    } while (input != 0)
}

fun printMenu() {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
}

fun showSeats(cinema: MutableList<MutableList<String>>) {
    println()
    println("Cinema:")
    print(" ")
    for (i in 1..cinema[0].size) print(" $i")
    println()
    for (i in 1..cinema.size) println("$i ${cinema[i - 1].joinToString(" ")}")
}

fun buyATicket(cinema: MutableList<MutableList<String>>, rows: Int, seats: Int) {
    println()
    println("Enter a row number:")
    val row = readln().toInt()
    println("Enter a seat number in that row:")
    val seat = readln().toInt()

    try {
        if (cinema[row - 1][seat - 1] == "B") {
            println()
            println("That ticket has already been purchased!")
            buyATicket(cinema, rows, seats)
        } else {
            cinema[row - 1][seat - 1] = "B"
            println()
            printPriceAndCountTickets(row, rows, seats)
        }
    } catch (e: IndexOutOfBoundsException) {
        println()
        println("Wrong input!")
        buyATicket(cinema, rows, seats)
    }
}

fun printPriceAndCountTickets(row: Int, rows: Int, seats: Int) {
    val ticketPrice: Int
    if (rows * seats <= 60 || row <= rows / 2) {
        ticketPrice = 10
        tenDollarTickets++
    } else {
        ticketPrice = 8
        eightDollarTickets++
    }
    println("Ticket price: $$ticketPrice")
}

fun statistics(rows: Int, seats: Int) {
    val totalTickets = tenDollarTickets + eightDollarTickets
    val percentage = totalTickets.toDouble() / (rows * seats).toDouble() * 100
    val formatPercentage = BigDecimal(percentage).setScale(2, RoundingMode.HALF_UP)
    val currentIncome = tenDollarTickets * 10 + eightDollarTickets * 8
    val totalIncome = if (rows * seats <= 60) {
        rows * seats * 10
    } else {
        rows / 2 * seats * 10 + (rows - rows / 2) * seats * 8
    }
    println()
    println("Number of purchased tickets: $totalTickets")
    println("Percentage: $formatPercentage%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}
