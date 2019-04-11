package com.worldsnas.kotlintesthelpers

import java.util.UUID
import kotlin.random.Random

fun randomInt() = Random.nextInt()
fun randomLong() = Random.nextLong()
fun randomDouble() = Random.nextDouble()
fun randomFloat() = Random.nextFloat()
fun randomBoolean() = Random.nextBoolean()
fun randomString() = UUID.randomUUID().toString()