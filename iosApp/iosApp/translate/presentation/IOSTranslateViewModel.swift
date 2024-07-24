//
//  IOSTranslateViewModel.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

extension TranslateScreen {
    @MainActor class IOSTranslateViewModel : ObservableObject {
        
        private var historyDataSource : HistoryDataSource
        private var translateUseCases : Translate
        
        private let viewModel : TranslateViewModel
        
        @Published var state = TranslateState(
            fromText: "",
            toText: nil,
            isTranslating:false,
            fromLanguage: UiLanguage(language: .english, imageName:"english"),
            toLanguage: UiLanguage(language: .hindi, imageName: "hindi"),
            isChoosingFromLanguage:false,
            isChoosingToLanguage:false,
            error: nil,
            history:[]
        )
        
        private var handle : DisposableHandle?
        
        init(historyDataSource: HistoryDataSource, translateUseCases: Translate) {
            self.historyDataSource = historyDataSource
            self.translateUseCases = translateUseCases
            self.viewModel = TranslateViewModel(translate:translateUseCases, historyDataSource: historyDataSource, coroutineScope: nil)
        }
        
        func onEvent(event:TranslateEvent){
            viewModel.onEvent(event:event)
        }
        
        
        func startObserving(){
            handle = viewModel.state.subscribe(onCollect: { state in
                if let state = state {
                    self.state = state
                }
            })
        }
        
        func dispose(){
            handle?.dispose()
        }
    }
}
