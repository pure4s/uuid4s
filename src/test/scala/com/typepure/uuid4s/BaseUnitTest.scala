package com.typepure.uuid4s

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar

trait BaseUnitTest
    extends FunSpec
    with Matchers
    with BeforeAndAfter
