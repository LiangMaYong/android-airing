# android-airing
this is android airing

Welcome Star and Issues

## Use this
1,observer
```
Airing.getDefault().observer(this).register("main", new OnAiringListener() {
    @Override
    public void onAiring(AiringContent content) {
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
}).register("action", new OnAiringListener() {
    @Override
    public void onAiring(AiringContent content) {
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
});
```
2,sender
```
//sendWhat
Airing.getDefault().sender("main").sendWhat(1);

//sendObject
Airing.getDefault().sender("main").sendObject(this);

//sendEmpty
Airing.getDefault().sender("main").sendEmpty();
```
3,unregister
```
Airing.unregister(this);
```
##License
```
Copyright 2016 LiangMaYong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
