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

const pauses = document.getElementsByClassName('pause');
const pausesArray = Array.from(pauses);
pausesArray.forEach(function(pause) {
	pause.addEventListener('click', function(){
		if (audio != null) {
			pausesArray.forEach(function(p) {
				p.classList.remove('show');
				p.classList.add('hide');
				const replay = p.nextElementSibling;
				replay.classList.remove('hide');
				replay.classList.add('show');
			});
			audio.pause();
		}
	});
});

const replays = document.getElementsByClassName('replay');
const replaysArray = Array.from(replays);
replaysArray.forEach(function(replay) {
	replay.addEventListener('click', function(){
		replaysArray.forEach(function(r) {
			r.classList.remove('show');
			r.classList.add('hide');
			const pause = r.previousElementSibling;
			pause.classList.remove('hide');
			pause.classList.add('show');
		});
		audio.play();
	});
});

playsArray.forEach(function(play) {
	play.addEventListener('click', function(){
		const bookName = document.getElementById('book_name');
		const chapterName = document.getElementById('chapter_name');
		const mp3FileName = play.nextElementSibling;
		if (audio != null) audio.pause();
		audio = new Audio(`../sound/${bookName.textContent}/${chapterName.textContent}/${mp3FileName.value}`);
		audio.play();
		replaysArray.forEach(function(replay) {
			replay.classList.remove('show');
			replay.classList.add('hide');
			const pause = replay.previousElementSibling;
			pause.classList.remove('hide');
			pause.classList.add('show');
		});
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
			judgement.textContent = '◯';
			correct.play();
		} else {
			judgement.textContent = '×';
			incorrect.play();
		}
	});
});
