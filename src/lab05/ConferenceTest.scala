package u05lab.ex2

import org.junit.{Assert, Before, Test}
import org.junit.Assert.*

class ConferenceTest:

  import u05lab.ex2.ConferenceReviewing
  import Question.*

  var cr: ConferenceReviewingImpl = ConferenceReviewingImpl()

  @Before
  def init(): Unit =
    this.cr.loadReview(1)(8)(8)(6)(8)
    this.cr.loadReview(1)(9)(9)(6)(9)
    this.cr.loadReview(2)(9)(9)(10)(9)
    this.cr.loadReview(2)(4)(6)(10)(6)
    this.cr.loadReview(3)(3)(3)(3)(3)
    this.cr.loadReview(3)(4)(4)(4)(4)
    this.cr.loadReview(4)(6)(6)(6)(6)
    this.cr.loadReview(4)(7)(7)(8)(7)
    this.cr.loadReview(4)(Map(Question.RELEVANCE -> 8, Question.SIGNFICANCE -> 8, Question.CONFIDENCE -> 7, Question.FINAL -> 8))
    this.cr.loadReview(5)(6)(6)(6)(10)
    this.cr.loadReview(5)(7)(7)(7)(10)

  @Test
  def testOrderedScores(): Unit =
    assertEquals(List(4, 9), this.cr.orderedScores(2)(Question.RELEVANCE))

  @Test
  def testAverageFinalScore(): Unit =
    assertEquals(8.5, this.cr.averageFinalScore(1),0.01)

  @Test
  def testAcceptedArticles(): Unit =
    assertEquals(Set(1, 2, 4), cr.acceptedArticles)

  @Test
  def testSortedAcceptedArticles(): Unit =
    assertEquals(List((1, 8.5), (2, 7.5), (4, 7.0)), cr.sortedAcceptedArticles)

  @Test
  def testAverageWeightedFinalScore(): Unit =
    assertEquals( (4.8+5.4)/2, cr.averageWeightedFinalScoreMap(1), 0.01 )


