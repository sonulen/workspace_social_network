package com.redmadrobot.domain.util

import io.kotest.assertions.assertSoftly
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

@ExperimentalKotest
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
    context("Passing the correct email's to a email validation function") {
        withData<String>(
            { "validate email [\"$it\"] should be true" },
            "chimi@gmail.com",
            "changa@mail.uk",
            "chimi@mail.ru",
            "changa@ya.mail.en",
            "chimi_changa@gmail.com",
            "email@example.com",
            "firstname.lastname@example.com",
            "email@subdomain.example.com",
            "firstname+lastname@example.com",
            "email@123.123.123.123",
            "1234567890@example.com",
            "email@example-one.com",
            "_______@example.com",
            "email@example.name",
            "email@example.museum",
            "email@example.co.jp",
            "firstname-lastname@example.com",
        ) {
            validator.isEmailValid(it) shouldBe true
        }
    }
    context("Passing the incorrect email's to a email validation function") {
        withData<String>(
            { "validate email [\"$it\"] should be false" },
            "chimi@g!mail.com",
            "@mail.uk",
            "chimi@mail",
            "@",
            ".",
            "!@example.com",
            "changa@.com",
            "chimichanga",
        ) {
            validator.isEmailValid(it) shouldBe false
        }
    }
    context("Passing the correct password's to a password validation function") {
        withData<String>(
            { "validate password [\"$it\"] should be true" },
            "MMMlkj19",
            "kjljMIO09",
            "0bG29bIb",
            "fvRPU4wD",
            "Toyd7sdZz",
            "avyqY7dRO",
            "fjPM8XFle",
            "lmH0EeNdT",
            "QzX04PSp",
            "HVGVXJi5",
            "ptq8ATvm",
            "rdldVms1",
        ) {
            validator.isPasswordValid(it) shouldBe true
        }
    }
    context("Passing the incorrect password's to a password validation function") {
        withData<String>(
            { "validate password [\"$it\"] should be false" },
            "MMMlkj1",
            "k",
            "0bG29",
            "fvRPUwD",
            "toyd7sdz",
            "avyqY7!dRO",
            "fjPM8@XFle",
            "lmH0E eNdT",
            "QzX04P\"Sp",
            "HVGVXJшi5",
            "ptq8ATШvm",
            "rdldV_ms1",
        ) {
            validator.isPasswordValid(it) shouldBe false
        }
    }
    context("Passing the correct nickname's to a nickname validation function") {
        withData<String>(
            { "validate nickname [\"$it\"] should be true" },
            "sonulen",
            "medivh",
            "CHIMI",
            "CHANGA",
            "1234908",
            "timmy098",
            "a",
        ) {
            validator.isNicknameValid(it) shouldBe true
        }
    }
    context("Passing the incorrect nickname's to a nickname validation function") {
        withData<String>(
            { "validate nickname [\"$it\"] should be false" },
            "Андрей",
            "chimi_changa",
            "chimi changa",
            "timmy!",
            "Jack@",
            "Joshик",
            "(a)",
            "!!!"
        ) {
            validator.isNicknameValid(it) shouldBe false
        }
    }
    context("Passing the correct name's to a name validation function") {
        withData<String>(
            { "validate name [\"$it\"] should be true" },
            "Josh",
            "Andrey",
            "Monika",
        ) {
            validator.isNameValid(it) shouldBe true
        }
    }
    context("Passing the incorrect name's to a name validation function") {
        withData<String>(
            { "validate name [\"$it\"] should be false" },
            "Андрей",
            "And rey",
            "Monika!",
            "!",
            "Joshi1",
            "121243"
        ) {
            validator.isNameValid(it) shouldBe false
        }
    }
    context("Passing the correct surname's to a surname validation function") {
        withData<String>(
            { "validate surname [\"$it\"] should be true" },
            "Tolmachev",
            "C",
            "JJ",
            "Churchill",
        ) {
            validator.isSurNameValid(it) shouldBe true
        }
    }
    context("Passing the incorrect surname's to a surname validation function") {
        withData<String>(
            { "validate surname [\"$it\"] should be false" },
            "Tolma chev",
            "Black1",
            "Black!",
            "0123948",
            "Chur-chill",
            "Spencer Churchill"
        ) {
            validator.isSurNameValid(it) shouldBe false
        }
    }
    context("Passing the correct birthday's to a birthday validation function") {
        withData<String>(
            { "validate birthday [\"$it\"] should be true" },
            "1993-07-29",
            "2007-01-01",
            "1999-12-31",
        ) {
            validator.isBirthDayValid(it) shouldBe true
        }
    }
    context("Passing the incorrect birthday's to a birthday validation function") {
        withData<String>(
            { "validate birthday [\"$it\"] should be false" },
            "29-07-1993",
            "1993/07/29",
            "one thousand nine hundred ninety-three",
            "1993 07 29",
            "1899-01-01",
            "7777-01-01",
            "0000-00-00",
            "1993-07-39",
            "1993-16-39",
            "0999-12-31",
            "1993-07-32",
            "1993-07-00",
            "1993-00-03",
        ) {
            validator.isBirthDayValid(it) shouldBe false
        }
    }

})
