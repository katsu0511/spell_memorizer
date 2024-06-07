const views = document.getElementsByClassName('view');
const viewsArray = Array.from(views);
viewsArray.forEach(function(view) {
  view.addEventListener('click', function(){
		view.classList.remove('show');
		view.classList.add('hide');
		const mask = view.nextElementSibling;
		mask.classList.remove('hide');
		mask.classList.add('show');
		
		const td = view.parentElement.parentElement;
		const spellInput = td.querySelector('.spell_input');
		const word = td.querySelector('.word');
		word.textContent = spellInput.value;
	});
});

const masks = document.getElementsByClassName('mask');
const masksArray = Array.from(masks);
masksArray.forEach(function(mask) {
  mask.addEventListener('click', function(){
		mask.classList.remove('show');
		mask.classList.add('hide');
		const view = mask.previousElementSibling;
		view.classList.remove('hide');
		view.classList.add('show');
		
		const word = mask.parentElement.parentElement.querySelector('.word');
		word.textContent = '**********';
	});
});

const plays = document.getElementsByClassName('play');
const playsArray = Array.from(plays);
let audio = null;
playsArray.forEach(function(play) {
  play.addEventListener('click', function(){
		const bookName = document.getElementById('book_name');
		const chapterName = document.getElementById('chapter_name');
		const mp3FileName = play.nextElementSibling;
		if (audio != null) {
			audio.pause();
		}
		audio = new Audio(`../sound/${bookName.textContent}/${chapterName.textContent}/${mp3FileName.value}`);
		audio.play();
	});
});

const answerButtons = document.getElementsByClassName('answer_button');
const answerButtonsArray = Array.from(answerButtons);
const correct = new Audio(`../sound/judgement/correct.mp3`);
const incorrect = new Audio(`../sound/judgement/incorrect.mp3`);
answerButtonsArray.forEach(function(answerButton) {
  answerButton.addEventListener('click', function(){
		const answer = answerButton.parentElement.parentElement.parentElement.querySelector('.spell_input').value;
		const myAnswer = answerButton.previousElementSibling.value;
		const judgement = answerButton.parentElement.parentElement.parentElement.querySelector('.judgement');
		judgement.classList.remove('correct');
		judgement.classList.remove('incorrect');
		correct.pause();
		correct.currentTime = 0;
		incorrect.pause();
		incorrect.currentTime = 0;
		if (answer === myAnswer) {
			judgement.classList.add('correct');
			judgement.textContent = '◯';
			correct.play();
		} else {
			judgement.classList.add('incorrect');
			judgement.textContent = '×';
			incorrect.play();
		}
	});
});
