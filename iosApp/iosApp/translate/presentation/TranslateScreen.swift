//
//  TranslateScreen.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateScreen: View {
    
    private var historyDataSource : HistoryDataSource
    private var translateUseCase : Translate
    
    @ObservedObject var viewmodel : IOSTranslateViewModel
    
    init(historyDataSource: HistoryDataSource, translateUseCase: Translate) {
        self.historyDataSource = historyDataSource
        self.translateUseCase = translateUseCase
        self.viewmodel = IOSTranslateViewModel(historyDataSource: historyDataSource, translateUseCases: translateUseCase)
    }
    
    var body: some View {
        ZStack {
            List {
                HStack(alignment: .center) {
                    LanguageDropDown(language: viewmodel.state.fromLanguage, isOpen: viewmodel.state.isChoosingToLanguage) { langauge in
                        viewmodel.onEvent(event: TranslateEvent.ChooseFromLanguage(language: langauge))
                    }
                    
                    Spacer()
                    
                    SwapLangaugeButton {
                        viewmodel.onEvent(event: TranslateEvent.SwapLanguages())
                    }
                    
                    Spacer()
                    
                    LanguageDropDown(language: viewmodel.state.toLanguage, isOpen: viewmodel.state.isChoosingToLanguage) { langauge in
                        viewmodel.onEvent(event: TranslateEvent.ChooseToLanguage(language: langauge))
                    }
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                TranslateTextField(
                    fromText: Binding(get: {viewmodel.state.fromText}, set: { text in  viewmodel.onEvent(event: TranslateEvent.ChangeTranslationText(text: text))}),
                    toText: viewmodel.state.toText,
                    isTranslating:viewmodel.state.isTranslating,
                    fromLanguage: viewmodel.state.fromLanguage,
                    toLanguage: viewmodel.state.toLanguage,
                    onTranslateEvent: {viewmodel.onEvent(event: $0) }
                )
                .listRowSeparator(.hidden)
                .listRowBackground(Color.background)
                
                if !viewmodel.state.history.isEmpty {
                    Text("History")
                        .font(.title)
                        .bold()
                        .frame(maxWidth: .infinity,alignment: .leading)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.background)
                    
                    ForEach(viewmodel.state.history,id: \.id){ item in
                        TranslateHistoryItem(item: item, onClick: { viewmodel.onEvent(event: TranslateEvent.SelectHistoryItem(item: item)) })
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color.background)
                    }
                }
                
            }
            .listStyle(.plain)
            .buttonStyle(.plain)
            
            VStack{
                Spacer()
                
                NavigationLink(destination: { Text("Voice to text screen") }) {
                    ZStack{
                       Circle()
                            .foregroundStyle(Color.primaryColor)
                            .padding()
                        Image(uiImage: UIImage(named: "mic")!)
                    }
                    .frame(maxWidth: 100,maxHeight: 100)
                }
            }
        }
        .onAppear(perform: {
            viewmodel.startObserving()
        })
        .onDisappear(perform: {
            viewmodel.dispose()
        })
    }
    
    
}

//#Preview {
//    TranslateScreen()
//}
