## FIUBA Android Examples

The main objective of this project is to provide a nice stack, examples and a list of do's/dont's of an Android application.

Bear in mind that this repository is aimed to students of Taller II from Facultad de Ingenieria UBA.

## Topics

#### Project Structure

From here you can see a good way for structuring your application. Note that there are a lot of ways, each with its own pros/cons. Pick always the one you feel more comfortable with.

Here I will show you the one I see the most across Android, with a few changes that I use for decoupling services / businesses.

Peek in all the files, as Im explaining whats the usage of it, and what does each line do!

#### Models

From here you can see how to create a Model / Entity / DTO. Although its just a POJO with getter/setters. I will also explain you how to serialize/parse the model from/to JSON with Gson.

Gson has a rich api, so if you need for example to serialize nulls or send to the backend underscore fields, simply change Gson configurations!

#### MVP

From here you can see how to implement a simple MVP structure. As always, note that there are a **lot** of ways of implementing MVP in Android (and already existent frameworks).

Since this isnt for a highly scalable productive application, we will use Square's Coordinators and a simple Contract logic made by ourselves. Which still its a really good MVP logic, but can be quite boilerplate (we are not using DI since its out of this scope).

Most of people like to implement MVP on Activities / Fragments as the Views. Might seem ok, but why did I use views and POJOs as presenters?
Ofc, this is just my opinion. But here are some reasons:
- Making the activity / fragment a whole view system can create a really coupled view, or even a god class (remember its still an activity!)
- Making the activity / fragment a whole presenter would expose to a presenter information it shouldnt know! (The presenter shouldnt know about the context)
- The activity should simply be an entry point of the system, from where you should interact with the android os api (for example, request a permission, manage the screen rotation, start activities, etc).
- The fragment it should be the same as an activity, but in a more decoupled state of the activity information.
- Still (In my opinion) a fragment is a fiendish monster no one should use, because its main objective is to decouple logic from the activity, but does quite the oposite:
  - It can easily be a god class
  - it adds a lot more of lifecycles than those we already have
  - Its not intuitive at all. Remember you shouldnt create constructors for them.
  - It has bad coding purposes like creating a headless retained fragment for api requests / permissions, that lives ""forever""
  - Two apis, should I inject it to a FrameLayout, use it in an XML or how do I use one?

#### Networking

From here you can see how to implement with Retrofit / Gson / Rx a communication with a backend, really easily.

#### Extras

In the extras section im showing some stuff you will deal with in your assignments. This are:
- Loading images from the web
- Using a Map

#### Others

- All the apis used are highly known by the community so if any doubt arises, just google it and you will most probably find the answer.
- Each module is stricted to only show what the scope aims for (this means, we wont do MVP in the Extras section, for the sake of understanding the Extras in particular).
- Note that although the stack contains a lot of really useful tools, there are a lot of problems in it that could be addressed with others (eg. Boilerplate with DI, Multidex with proguard,
Push Notifications with Firebase, etc). Since they didn't deem critical for the assignment, I left them out.