package u05lab.ex2

enum Question:
  case RELEVANCE
  case SIGNFICANCE
  case CONFIDENCE
  case FINAL

trait ConferenceReviewing:
  def loadReview(article: Int)(scores: Map[Question, Int]): Unit
  def loadReview(article: Int)(relevance: Int)(significance: Int)(confidence: Int)(fin: Int): Unit
  def orderedScores(article: Int)(question: Question): List[Int]
  def averageFinalScore(article: Int): Double
  def acceptedArticles: Set[Int]
  def sortedAcceptedArticles: List[(Int, Double)]
  def averageWeightedFinalScoreMap: Map[Int, Double]

case class ConferenceReviewingImpl() extends ConferenceReviewing:

  private var reviews: List[(Int, Map[Question, Int])]  = List();

  override def loadReview(article: Int)(scores: Map[Question, Int]): Unit =
    if scores.size >= Question.values.length then this.reviews = this.reviews :+ (article, scores) else throw IllegalArgumentException()
  override def loadReview(article: Int)(relevance: Int)(significance: Int)(confidence: Int)(fin: Int): Unit =
    val map: Map[Question, Int] = Map(Question.RELEVANCE -> relevance, Question.SIGNFICANCE -> significance,
      Question.CONFIDENCE -> confidence, Question.FINAL -> fin)
    this.reviews = this.reviews :+ (article, map)
  override def orderedScores(article: Int)(question: Question): List[Int] =
    this.reviews.filter(_._1 == article)
      .map(_._2(question))
      .sorted
  override def averageFinalScore(article: Int): Double =
    this.reviews.filter(_._1 == article).map(_._2(Question.FINAL)).sum.toDouble / this.reviews.count(_._1 == article)
  private def accepted(article: Int): Boolean =
    this.averageFinalScore(article) > 5.0 && this.reviews.filter(_._1 == article)
      .map(_._2(Question.RELEVANCE)).exists(_ >= 8)
  override def acceptedArticles: Set[Int] = this.reviews.map(_._1).distinct.filter(this.accepted).toSet
  override def sortedAcceptedArticles: List[(Int, Double)] = this.acceptedArticles.map(x => (x, this.averageFinalScore(x))).toList.sorted
  private def averageWeightedFinalScore(article: Int): Double =
    this.reviews.filter(_._1 == article).map(x => x._2(Question.FINAL) * x._2(Question.CONFIDENCE) /
      10.0).sum / this.reviews.count(_._1 == article).toDouble
  override def averageWeightedFinalScoreMap: Map[Int, Double] =
    this.reviews.map(_._1).distinct.map(x => (x, this.averageWeightedFinalScore(x))).toMap




