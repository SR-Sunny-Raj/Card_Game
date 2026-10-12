var myData = {};

document
  .getElementById('start-game-button')
  .addEventListener('click', startGame);
document
  .getElementById('player1-input')
  .addEventListener('keydown', handleNameInputKeydown);
document
  .getElementById('player2-input')
  .addEventListener('keydown', handleNameInputKeydown);
document
  .getElementById('player1-input')
  .addEventListener('input', toggleStartGameButton);
document
  .getElementById('player2-input')
  .addEventListener('input', toggleStartGameButton);
document.getElementById('new-game-button').addEventListener('click', resetGame);

document
  .getElementById('img-1')
  .addEventListener('click', reveal.bind(this, 1));
document
  .getElementById('img-2')
  .addEventListener('click', reveal.bind(this, 2));
document
  .getElementById('img-3')
  .addEventListener('click', reveal.bind(this, 3));
document
  .getElementById('img-4')
  .addEventListener('click', reveal.bind(this, 4));
document
  .getElementById('img-5')
  .addEventListener('click', reveal.bind(this, 5));
document
  .getElementById('img-6')
  .addEventListener('click', reveal.bind(this, 6));
document
  .getElementById('button-1')
  .addEventListener('click', reveal.bind(this, 7));
document
  .getElementById('button-2')
  .addEventListener('click', reveal.bind(this, 8));
document
  .getElementById('final-button')
  .addEventListener('click', reveal.bind(this, 9));

// starts the round on Enter only once both name fields are filled
function handleNameInputKeydown(event) {
  if (event.key !== 'Enter') {
    return;
  }
  var player1Name = document.getElementById('player1-input').value.trim();
  var player2Name = document.getElementById('player2-input').value.trim();
  if (player1Name && player2Name) {
    startGame();
  }
}

// keeps Start Game disabled until both player names are entered
function toggleStartGameButton() {
  var player1Name = document.getElementById('player1-input').value.trim();
  var player2Name = document.getElementById('player2-input').value.trim();
  document.getElementById('start-game-button').disabled =
    !player1Name || !player2Name;
}

// collects the names entered in the modal and fetches a fresh round from the servlet
function startGame() {
  var player1Name =
    document.getElementById('player1-input').value.trim() || 'Player1';
  var player2Name =
    document.getElementById('player2-input').value.trim() || 'Player2';

  var url =
    './playGame?player1=' +
    encodeURIComponent(player1Name) +
    '&player2=' +
    encodeURIComponent(player2Name);

  fetch(url)
    .then((response) => response.json())
    .then((data) => {
      myData = data;

      document.getElementById('name-modal').style.display = 'none';
      document.getElementById('game-board').style.display = 'grid';
      document.getElementById('final').style.display = 'block';

      document.getElementById('name-1').innerHTML =
        'Name: ' + myData.player1name;
      document.getElementById('name-2').innerHTML =
        'Name: ' + myData.player2name;
    });
}

// resets the board and brings back the name modal for another round
function resetGame() {
  document.getElementById('player1-input').value = '';
  document.getElementById('player2-input').value = '';
  document.getElementById('start-game-button').disabled = true;
  document.getElementById('name-modal').style.display = 'flex';
  document.getElementById('game-board').style.display = 'none';
  document.getElementById('final').style.display = 'none';
  document.getElementById('new-game-button').style.display = 'none';
  document.getElementById('win').innerHTML = '';
  document.getElementById('score').innerHTML = '';
  document.getElementById('points-1').innerHTML = '0';
  document.getElementById('points-2').innerHTML = '0';

  ['img-1', 'img-2', 'img-3', 'img-4', 'img-5', 'img-6'].forEach(function (id) {
    var element = document.getElementById(id);
    element.src = './images/back.png';
    element.value = 'not-revealed';
  });
  ['img-high-1', 'img-high-2'].forEach(function (id) {
    var element = document.getElementById(id);
    element.src = './images/blank_card.png';
    element.value = 'not-revealed';
    element.style.display = 'none';
  });

  document.getElementById('button-1').setAttribute('disabled', 'disabled');
  document.getElementById('button-2').setAttribute('disabled', 'disabled');
  document.getElementById('final-button').setAttribute('disabled', 'disabled');

  document.getElementById('box-1').classList.remove('winner-box', 'loser-box');
  document.getElementById('box-2').classList.remove('winner-box', 'loser-box');
}

function reveal(num) {
  if (num == 1) {
    var element = document.getElementById('img-1');
    var card = myData.p1card1;
    setImg(element, card);
  }
  if (num == 2) {
    var element = document.getElementById('img-2');
    var card = myData.p1card2;
    setImg(element, card);
  }
  if (num == 3) {
    var element = document.getElementById('img-3');
    var card = myData.p1card3;
    setImg(element, card);
  }
  if (num == 4) {
    var element = document.getElementById('img-4');
    var card = myData.p2card1;
    setImg(element, card);
  }
  if (num == 5) {
    var element = document.getElementById('img-5');
    var card = myData.p2card2;
    setImg(element, card);
  }
  if (num == 6) {
    var element = document.getElementById('img-6');
    var card = myData.p2card3;
    setImg(element, card);
  }
  if (num == 7) {
    var element1 = document.getElementById('img-high-1');
    var element2 = document.getElementById('points-1');
    var card = myData.p1highestcard;
    var score = myData.p1highestscore;
    element1.src =
      './images/' +
      card.split('-')[0].toLowerCase() +
      '/' +
      card.split('-')[1].toLowerCase() +
      '.png';
    element1.value = 'revealed';
    element1.style.display = 'block';
    element2.innerHTML = score;
    document.getElementById('button-1').setAttribute('disabled', 'disabled');
  }
  if (num == 8) {
    var element1 = document.getElementById('img-high-2');
    var element2 = document.getElementById('points-2');
    var card = myData.p2highestcard;
    var score = myData.p2highestscore;
    element1.src =
      './images/' +
      card.split('-')[0].toLowerCase() +
      '/' +
      card.split('-')[1].toLowerCase() +
      '.png';
    element1.value = 'revealed';
    element1.style.display = 'block';
    element2.innerHTML = score;
    document.getElementById('button-2').setAttribute('disabled', 'disabled');
  }
  if (num == 9) {
    var element1 = document.getElementById('win');
    var element2 = document.getElementById('score');
    var game_winner = myData.winner;
    var highest_score = myData.highestscore;
    element1.innerHTML = 'Winner is -: ' + game_winner;
    element2.innerHTML = 'Highest Score is -: ' + highest_score;
    document.getElementById('new-game-button').style.display = 'inline-block';
    document
      .getElementById('final-button')
      .setAttribute('disabled', 'disabled');

    var box1 = document.getElementById('box-1');
    var box2 = document.getElementById('box-2');
    if (myData.p1highestscore > myData.p2highestscore) {
      box1.classList.add('winner-box');
      box2.classList.add('loser-box');
    } else if (myData.p2highestscore > myData.p1highestscore) {
      box2.classList.add('winner-box');
      box1.classList.add('loser-box');
    }
  }

  if (
    document.getElementById('img-1').value == 'revealed' &&
    document.getElementById('img-2').value == 'revealed' &&
    document.getElementById('img-3').value == 'revealed'
  ) {
    document.getElementById('button-1').removeAttribute('disabled');
  }
  if (
    document.getElementById('img-4').value == 'revealed' &&
    document.getElementById('img-5').value == 'revealed' &&
    document.getElementById('img-6').value == 'revealed'
  ) {
    document.getElementById('button-2').removeAttribute('disabled');
  }
  if (
    document.getElementById('img-high-1').value == 'revealed' &&
    document.getElementById('img-high-2').value == 'revealed'
  ) {
    document.getElementById('final-button').removeAttribute('disabled');
  }
}

function setImg(element, card) {
  element.src =
    './images/' +
    card.split('-')[0].toLowerCase() +
    '/' +
    card.split('-')[1].toLowerCase() +
    '.png';
  element.value = 'revealed';
}
