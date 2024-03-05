# GitHubClient

<image width="400" src="./art/screen_recording.gif" />

## 環境

- JDK 17
- Android Studio Iguana 以上

## アーキテクチャ

MVVM + Redux

<image width="400" src="./art/architecture.png" />

## モジュール構成

```mermaid
graph TD;
    :feature:xx-->:core:ui
    :feature:xx-->:data:repository;
    :data:repository-impl-->:data:repository;
    :data:repository-impl-->:data:api;
    :data:api-impl-->:data:api
```
