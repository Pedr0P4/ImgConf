# ImgConf

## 🫂 Propósito do projeto 🫂
Esse projeto tem como objetivo facilitar na edição rápida de algumas imagens, tais como redimensioná-las, adicionar filtros rápidos, ajustar o brilho e saturação e também girar horizontalmente ou verticalmente.
<br>

## 👥 Participantes 👥
Nome: Pedro Paulo Galvão Galindo de Oliveira
<br>
Matrícula: 20230078772
<br>

## 📁 Organização do projeto 📁
O projeto é separado em um AppStructure (o coração do peojeto, onde a mágica acontece) que tem toda a parte visual do projeto (menus e informações, além da imagem carregada pelo usuário). Cada menu possui funcionalidades que são implementadas pelas classes do pacote "menubar" e os atalhos são implementados pelas classes do pacote "shortcuts".

O pacote "other" implementa funcionalidades específicas e/ou genéricas, como a classe Component, que é usada por todos as classes do menu e os atalhos (através de herança), pois ela carrega as informações de AppStructure (como se compartilhasse as infos de AppStructure para facilitar na comunicação entre as classes).

A classe ResizeDialog também faz parte do pacote "other", pois ela serve apenas para mostrar uma caixa personalizada para definir a nova largura e altura da imagem (utilizada apenas no menu Edit). Por fim, a interface funcional ActionExecutor serve para passar métodos como parâmetro de outros métodos, como, por exemplo, acontece nos atalhos do teclado, onde um dos parâmetros serve para definir o que aquele atalho faz, sendo necessário passar um método como parâmetro.
<br>

## 🔧 Build e Run 🔧
Para dar build no projeto, basta abrir o terminal na pasta raíz e dar um 
```bash
./gradlew build
```
E para dar run, basta abrir o terminal na pasta raiz e dar um
```bash
./gradlew run
```
<br>

## 🎥 Link do vídeo de apresentação do programa 🎥
<div>
    <a href="https://www.loom.com/share/33300284bd544c97a59b453ab0c2b807">
      <p>Projeto Final Unidade III - Explicação do projeto (requisitos e afins)</p>
    </a>
    <a href="https://www.loom.com/share/33300284bd544c97a59b453ab0c2b807">
      <img style="max-width:300px;" src="https://cdn.loom.com/sessions/thumbnails/33300284bd544c97a59b453ab0c2b807-3b4cc17b4e4297f0-full-play.gif">
    </a>
  </div>

<div>
    <a href="https://www.loom.com/share/d607e2dc78394dc78d7aba22c7e97fa1">
      <p>Projeto Final Unidade III - ImgConf (Demonstração)</p>
    </a>
    <a href="https://www.loom.com/share/d607e2dc78394dc78d7aba22c7e97fa1">
      <img style="max-width:300px;" src="https://cdn.loom.com/sessions/thumbnails/d607e2dc78394dc78d7aba22c7e97fa1-30022067e3a0291f-full-play.gif">
    </a>
  </div>

##
[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/jidp6Ter)
