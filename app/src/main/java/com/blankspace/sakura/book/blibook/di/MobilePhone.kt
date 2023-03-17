package com.blankspace.sakura.book.blibook.di

import javax.inject.Inject

class MobilePhone @Inject constructor(private val simCard: SimCard) {
    fun dialNumber()
    {
        simCard.dialNumber()
    }
}