package io.kotest.datatest

import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe

@ExperimentalKotest
class RootDataTestNameFunctionTest : FunSpec({

   withData(
      Pair(2, 1),
      Pair(1, 2),
   ) {}

   withData(
      Triple(1, 2, 3),
      Triple(3, 2, 1),
   ) {}

   withData(
      ADummyClass("a1", "b1"),
      ADummyClass("a2", "b2")
   ) {}

   withData(
      AnotherDummyClass("a1", "b1"),
      AnotherDummyClass("a2", "b2")
   ) {}

   withData<SimpleClass>(
      { "simple${it.a}${it.b}" },
      SimpleClass("a1", "b1"),
      SimpleClass("a2", "b2"),
   ) {}

   withData<SimpleClass>(
      { "simple${it.a}${it.b}" },
      sequenceOf(
         SimpleClass("a1", "b1"),
         SimpleClass("a2", "b2"),
      )
   ) {}

   withData<SimpleClass>(
      { "simple${it.a}${it.b}" },
      listOf(
         SimpleClass("a1", "b1"),
         SimpleClass("a2", "b2"),
      )
   ) {}

}) {
   override fun afterAny(testCase: TestCase, result: TestResult) {
      DataTestNamesStore.names.add(testCase.descriptor.id.value)
   }

   override fun afterSpec(spec: Spec) {
      DataTestNamesStore.names shouldBe listOf(
         "(2, 1)",
         "(1, 2)",
         "(1, 2, 3)",
         "(3, 2, 1)",
         "ADummyClass(a : a1, b : b1)",
         "ADummyClass(a : a2, b : b2)",
         "AnotherDummyClass(a : a1, b : b1)",
         "AnotherDummyClass(a : a2, b : b2)",
         "simplea1b1",
         "simplea2b2",
         "simplea1b1",
         "simplea2b2",
         "simplea1b1",
         "simplea2b2",
      )
   }

   override fun beforeSpec(spec: Spec) {
      DataTestNamesStore.names.clear()
   }
}