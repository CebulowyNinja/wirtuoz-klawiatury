# Wirtuoz klawiatury
Projekt edukacyjny gry mającej na celu zwiększenie szybkości oraz ergonomii pisania

*W trakcie tworzenia projektu opis oraz commity reazliwane będą w języku polskim.*

## Główne cele oraz założenia projektu
* Tryb rozgrywki wymagający od gracza wpisywania fraz
* Tryb rozgrywki pozwalający na doskonalenie wpisywania pojedynczych znaków
* Możliwość wyboru przez gracza dowolnej aplikacji klawiatury ekranowej (zainstalowanej przez niego lub preinstalowanej przez producenta)
* Przyjazny i klarowny interfejs rozgrywki skupiający uwagę gracza na frazach lub znakach do wpisania
* Statystyki z odbytych gier pozwalające na ocenę ergonomii korzystania z różnych klawiatur

## Analiza możliwości realizacji założeń projektowych
Frazy wykorzystywane w pierwszym trybie rozgrywki zostaną stworzone przy założeniu, że powinny być łatwe do zapamiętania, typowe dla języka polskiego oraz mieć przeciętną długość.
> „In creating a phrase set, the goal is to use phrases that are moderate in length, easy to remember, and representative of the target language.” – I. Scott MacKenzie, R. William Soukoreff – Phrase Sets for Evaluating Text Entry Techniques,  CHI '03 Extended Abstracts on Human Factors in Computing Systems, 2003

Możliwość zmiany klawiatury jest wbudowaną funkcjonalnością systemu Android. Dzięki wykorzystaniu API m. in. klasy `InputMethodManager` możliwe jest wykrycie aktualnie używanej klawiatury, co pozwoli na automatyczne grupowanie statystyk według użytej aplikacji klawiatury ekranowej.

Statystyki będą prezentowane użytkownikowi poprzez liczbowe wskaźniki, w szczególności dla trybu wpisywania dłuższych fraz poprzez WPM (ang. Words per Minute, pol. słowa na minutę).
> „WPM is defined as: 𝑊𝑃𝑆=  |𝑇|/𝑆∙60∙1/5” – Ahmed Sabbir Arif, Wolfgang Stuerzlinger – Analysis of text entry performance metrics – 2009 IEEE Toronto International Conference Science and Technology for Humanity (TIC-STH), 2009

## Wstępny opis przebiegu gry
* Na ekranie startowym znajdować się będą przyciski wyboru trybu pierwszego oraz drugiego, otwarcia ekranu statystyk i otwarcia ekranu ewentualnych ustawień
* Po kliknięciu przycisku wyboru trybu otworzy się ekran gry, na który nałożony będzie przycisk którego kliknięcie będzie rozpoczynało rozgrywkę
* Na ekranie gry widoczna będzie ilość pozostałych do wpisania fraz lub znaków oraz przycisk zakończenia gry
* Po zakończonej grze gracz zostanie poinformowany o swoim wyniku oraz będzie mógł kliknąć przycisk ponownego uruchomienia gry, powrotu do menu lub przejścia do ekranu statystyk
* Ekran statystyk złożony będzie z przewijanej listy dostępnych klawiatur ekranowych, zawierającej szczegółowe statystyki dla każdej z klawiatur oraz przycisk resetowania statystyk danej klawiatury
