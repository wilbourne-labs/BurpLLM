<H1>Custom LLMs for BurpSuite</H1>

This repository holds several Java classes that can be used to interact with LLMs ostensibly to support the development of Burp Suite extensions. The classes can be included within an extensions source directly or bundled into a .jar and called as a library.

Two client classes are included for Open AI and Ollama. Configurations for these clients are held within ```/src/main/resources/config.properties```, with an example for Ollama shown below:

```
model=llama3.2
apiUrl=http://localhost:11434/api/generate
apiKey=null
```
Note: The Ollama client will run very slowly when Ollama is hosted on a non-dedicated machine.

<H2>Calling the client class</H2>

The implementation tracks closely to Portswigger's to support cross-compatibility between existing extensions and private developments. An example extension has been built and shared based on the sample AI extension provided by Portswigger (https://github.com/PortSwigger/burp-extensions-montoya-api-examples/tree/main/ai).

In this example, the customisable classes are called in a similar way to the original Portswigger example:

```
Original example:
PromptResponse promptResponse = ai.prompt().execute(promptMessageHandler.build(request.toString()));

Customisable example:
OllamaApiClient ollamaApiClient = new OllamaApiClient(); 
PromptResponse promptResponse = ollamaApiClient.executePrompt(PromptOptions.promptOptions(),promptMessageHandler.build(request.toString()));
```

<H2>Disclaimer</H2>
This is an open-source project designed to assist cybersecurity professionals in conducting authorised security assessments. This software is intended for legitimate use only, such as authorised penetration testing and/or non-profit educational purposes. It should only be used on systems or networks that you own or have explicit written permission from the owner of these systems or networks to perform testing. 

Misuse of this software for illegal activities, including unauthorised network intrusion, hacking, or any activity that violates applicable laws, is strictly prohibited. Wilbourne, contributors, and any affiliated party assume no responsibility or liability for any damage, misuse, or legal consequences arising from the use of this software. By using our project, you agree to indemnify and hold harmless the project contributors from any claims or legal action.

It is the user's sole responsibility to ensure compliance with all applicable local, state, national, and international laws. Use this software at your own risk.
