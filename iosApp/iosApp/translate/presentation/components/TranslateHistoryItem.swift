//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    
    let item:UiHistoryItem
    let onClick:() -> Void
    
    var body: some View {
        Button(action:onClick){
            VStack(alignment:.leading){
                
                HStack{
                    SmallLangaugeIcon(langauge: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundStyle(Color.lightBlue)
                        .font(.body)
                }
                .padding(.bottom)
                .frame(maxWidth: .infinity,alignment: .leading)
                
                HStack{
                    SmallLangaugeIcon(langauge: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundStyle(Color.onSurface)
                        .font(.body.weight(.semibold))
                }
                .frame(maxWidth: .infinity,alignment: .leading)
                
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 4)
            
        }
    }
}

#Preview {
    TranslateHistoryItem(
        item: UiHistoryItem(
            id: 0,
            fromText: "Hello",
            toText: "Hello",
            fromLanguage: UiLanguage(language: .english, imageName:"english"),
            toLanguage: UiLanguage(language: .hindi, imageName: "hindi"))
        ,
        onClick: {}
        )
}
