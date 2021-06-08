package com.redmadrobot.domain.util

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class AuthValidatorTest : FunSpec({
    val validator = AuthValidatorImpl()

    context("Passing the empty string to a validation function") {
        withData<String>(
            { "validate empty string [\"$it\"] should be false" },
            "",
            "    ",
            " \n",
            "  \t\n",
        ) {
            assertSoftly {
                validator.isEmailValid(it) shouldBe false
                validator.isPasswordValid(it) shouldBe false
                validator.isNicknameValid(it) shouldBe false
                validator.isNameValid(it) shouldBe false
                validator.isSurNameValid(it) shouldBe false
                validator.isBirthDayValid(it) shouldBe false
            }
        }
    }
    context("Email validation function tests") {
        withData<Pair<String, Boolean>>(
            "chimi@gmail.com" to true,
            "changa@mail.uk" to true,
            "chimi@mail.ru" to true,
            "changa@ya.mail.en" to true,
            "chimi_changa@gmail.com" to true,
            "email@example.com" to true,
            "firstname.lastname@example.com" to true,
            "email@subdomain.example.com" to true,
            "firstname+lastname@example.com" to true,
            "email@123.123.123.123" to true,
            "1234567890@example.com" to true,
            "email@example-one.com" to true,
            "_______@example.com" to true,
            "email@example.name" to true,
            "email@example.museum" to true,
            "email@example.co.jp" to true,
            "firstname-lastname@example.com" to true,
            "chimi@g!mail.com" to false,
            "@mail.uk" to false,
            "chimi@mail" to false,
            "@" to false,
            "." to false,
            "!@example.com" to false,
            "changa@.com" to false,
            "chimichanga" to false,
        ) { (value, expected) ->
            validator.isEmailValid(value) shouldBe expected
        }
    }
    context("Password validation function tests") {
        withData<Pair<String, Boolean>>(
            "MMMlkj19" to true,
            "kjljMIO09" to true,
            "0bG29bIb" to true,
            "fvRPU4wD" to true,
            "Toyd7sdZz" to true,
            "avyqY7dRO" to true,
            "fjPM8XFle" to true,
            "lmH0EeNdT" to true,
            "QzX04PSp" to true,
            "HVGVXJi5" to true,
            "ptq8ATvm" to true,
            "rdldVms1" to true,
            "MMMlkj1" to false,
            "k" to false,
            "0bG29" to false,
            "fvRPUwD" to false,
            "toyd7sdz" to false,
            "avyqY7!dRO" to false,
            "fjPM8@XFle" to false,
            "lmH0E eNdT" to false,
            "QzX04P\"Sp" to false,
            "HVGVXJшi5" to false,
            "ptq8ATШvm" to false,
            "rdldV_ms1" to false,
        ) { (value, expected) ->
            validator.isPasswordValid(value) shouldBe expected
        }
    }
    context("Nickname validation function tests") {
        withData<Pair<String, Boolean>>(
            "sonulen" to true,
            "medivh" to true,
            "CHIMI" to true,
            "CHANGA" to true,
            "1234908" to true,
            "timmy098" to true,
            "a" to true,
            "Андрей" to false,
            "chimi_changa" to false,
            "chimi changa" to false,
            "timmy!" to false,
            "Jack@" to false,
            "Joshик" to false,
            "(a)" to false,
            "!!!" to false,
        ) { (value, expected) ->
            validator.isNicknameValid(value) shouldBe expected
        }
    }
    context("Name validation function tests") {
        withData<Pair<String, Boolean>>(
            "Josh" to true,
            "Andrey" to true,
            "Monika" to true,
            "Андрей" to false,
            "And rey" to false,
            "Monika!" to false,
            "!" to false,
            "Joshi1" to false,
            "121243" to false,
        ) { (value, expected) ->
            validator.isNameValid(value) shouldBe expected
        }
    }
    context("Surname validation function tests") {
        withData<Pair<String, Boolean>>(
            "Tolmachev" to true,
            "C" to true,
            "JJ" to true,
            "Churchill" to true,
            "Tolma chev" to false,
            "Black1" to false,
            "Black!" to false,
            "0123948" to false,
            "Chur-chill" to false,
            "Spencer Churchill" to false,
        ) { (value, expected) ->
            validator.isSurNameValid(value) shouldBe expected
        }
    }
    context("Birthday validation function tests") {
        withData<Pair<String, Boolean>>(
            "1993-07-29" to true,
            "2007-01-01" to true,
            "1999-12-31" to true,
            "29-07-1993" to false,
            "1993/07/29" to false,
            "one thousand nine hundred ninety-three" to false,
            "1993 07 29" to false,
            "1899-01-01" to false,
            "7777-01-01" to false,
            "0000-00-00" to false,
            "1993-07-39" to false,
            "1993-16-39" to false,
            "0999-12-31" to false,
            "1993-07-32" to false,
            "1993-07-00" to false,
            "1993-00-03" to false,
        ) { (value, expected) ->
            validator.isBirthDayValid(value) shouldBe expected
        }
    }
})
