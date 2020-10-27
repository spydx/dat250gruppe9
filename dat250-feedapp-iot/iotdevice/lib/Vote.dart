class Vote {
  bool _answer;

  Vote(this._answer);

  bool get answer => _answer;

  set answer(bool value) {
    _answer = value;
  }
}