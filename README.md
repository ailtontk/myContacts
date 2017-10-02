# MyContacts
É um aplicativo para armazenar seus contatos! Crie, edite ou remove seus contatos de acordo com seu gosto!

## Libraries
As libraries escolhidas foram:<br><br>
<b>ButterKnife</B><br>
É uma das libraries mais utilizadas e torna a localização das views na tela muito mais fácil. Além dos eventos de clique.

<b>Retrofit</B><br>
É a library de comunicação mais rápida e usada no mundo todo! Além de simples de utilizar!

<b>GreenDAO</B><br>
Esta library foi escolhida para facilitar o mapeamento do objeto no banco. Neste cenário também valeria a pena utilizar o meio nativo, 
mas escolhi esta library para limpar ainda mais o código.

<b>Glide</B><br>
Dentre as mais populares libraries de carregamento de imagens, esta é uma das mais simples de utilizar e evitar muitos erros de outOfMemory como ocorre no Picasso.

<b>Material Search</B><br>
Esta foi utilizada para facilitar a criação do botão de pesquisa na tela inicial.

<b>Circular ImageView</B><br>
Esta foi utilizada para facilitar a criação de imagens circulares sem a necessidade de custom views ou métodos para modificação.

## Generating the App
Não há segredo aqui! Baixa executar o projeto ou no Android Studio, clicar em build > Generate APK para gerar um APK para instalação.

## About the code
O projeto está sobre a estrutura MVC possuindo 3 camadas de responsabilidades, sendo elas: UI, RN e as camadas de dados.

<b>UI</B><br>
A Camada de UI é composta por activities, fragments, adapters ou views customizadas. O nível de responsabilidade desta camada é controlar os elementos visiveis ao usuário e solicitar dados para a camada RN. Os membros desta camada jamais terão acesso direto ao banco de dados ou classes contendo chamadas de rede.

<b>RN</B><br>
A Camada da RN é composta por classes que realizam a regra de negócio dos objetos e telas. Normalmente todo objeto tem uma classe própria com sua própria regra de negócio. Neste cenário existe somente um POJO entitulado Contact que possui uma classe RN para buscar informações no banco, rede, preferências ou arquivos que sejam diretamente relacionadas ao POJO. <br><br>

A camada RN é a única que possui acesso á UI e as demais camadas de dados (como banco ou rede, por exemplo). Os membros seguem algumas regras de criação:

<ul>
<li>Não devem saber quem o está invocando</li>
<li>Cuidará dos processos em background assegurando a resposta para quem o invocou</li>
<li>Responde quem o invocou, mesmo quando não existe resultado</li>
<li>Não atualiza elementos da tela</li>
<li>Não acessa diretamente dados de responsabilidade de outra camada (como banco de dados ou rede)</li>
</ul>

<b>Camadas de dados</B><br>
Aqui pode existir dezenas de camadas distintas, onde a responsabilidade dos membros é conseguir adicionar, atualizar ou remover dados de uma fonte distinta. Os membros mais comuns são as DAOs (operações no banco de dados), interface de rede ou arquivos. Vale lembrar que os membros dessa camada somente devem conter as classes que retornam os dados de forma generica. Regras de negócio não são aplicadas aqui.

![estrutura](https://user-images.githubusercontent.com/19216148/31062327-3450a220-a700-11e7-921c-8d0d4243538b.png)

<b>Comunicação entre camadas: Conhecendo a interface IContactComponent</B><br>
Imagine o seguinte cenário: você está executando alguma lógica complexa em uma Task e quando a lógica for encerrada, sua Activity ou Fragment precisam saber do resultado para mudar de tela ou realizar alguma alteração na tela. Imagine que essa Task é usada por 10 activities diferentes, qual seria a melhor forma de reportar para a Activity que a task encerrou? Muitos desenvolvedores passam a Activity ou Fragment via construtor para a Task e no final, fazem um switch ou if com 10 opções diferentes para chamar um determinado método dentro da Activity ou Fragment. Isso de fato irá funcionar, mas em um projeto com 50 activities ou fragments diferentes utilizando a mesma task, teríamos o trabalho de incrementar um novo if ou case no final e chamar um determinado método, o que não é nada prático!<br>
Existem diversas formas de realizar essa comunicação entre os componentes, podendo ser via Broadcast Receiver, programação reativa, pela biblioteca Event bus e etc... Neste cenário, utilizamos a interface <b>IComponentContact</b>.<br>
Basicamente existe um método que será chamado com algumas informações de identificação do componente que está informando para que o componente que está sendo informado consiga identificar a classe que invocou o método e a ação que ela deseja realizar. A imagem abaixo representa o corpo do método.<br>
 
 ![image](https://user-images.githubusercontent.com/19216148/31062751-6540e2b6-a703-11e7-8218-745089b3ccba.png)<br><br>

As informações que devem ser passadas são:<br>
<ul>
<li><b>Classe de origem</b>: É a classe de onde a informação está vindo, pode ser passado com getClass() ou NomeDaClasse.class;</li>
<li><b>ID</b>: O id é um identificador da ação que você deseja fazer quando o componente receber a informação. Por exemplo, você possui dois botões e deseja informar a outro componente quando esses botões forem pressionados. Você precisará utilizar um identificador para que o componente que está recebendo saiba qual dos botões foi pressionado. Neste caso você pode utilizar o próprio id do botão, ou criar uma variável estática na classe <b>Param.java</b> (no pacote útil) e utiliza-la para identificar a ação.</li>
<li><b>Result</b>: É uma forma fácil de informar se a ação que você estava esperando foi realizada ou não com sucesso.</li>
<li><b>Objetos</b>: Não é obrigatório. Você pode criar um array de objetos, ou passar um único objeto para a classe que está sendo informada. Ao receber, basta dar um cast para o formato original do objeto que você passou.</li>
</ul>

![image](https://user-images.githubusercontent.com/19216148/31062814-e1ff001c-a703-11e7-9904-7a279fd54c8a.png)<br><br>

Para receber as informações nas activities e fragments, basta sobrescrever o método <b>onReceiveData</b> na activity ou fragment. O método já é tratado e é livre de erros e concorrências, além de transmitir todas as chamadas para a thread principal. Caso seja implementado por outra classe, basta implementar o método <b>onContactComponent</b>.<br><br>

![image](https://user-images.githubusercontent.com/19216148/31062815-e46e92ae-a703-11e7-8c3d-6197692dd2de.png)

## What I would change
Se houvesse mais tempo, implementaria novas formas de exibir os contatos na lista inicial sendo elas: através de ordenação, favoritos e mais acessados.<br>
Além disso implementaria a função de tirar foto do contato na hora e um novo campo para adicionar o telefone e ligar automáticamente pelo aplicativo.
