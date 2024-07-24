//
//  ProgressButton.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProgressButton: View {
    
    var text:String
    var isLoading:Bool
    var onClick:() -> Void
    
    var body: some View {
        
        Button(action: {
            if(!isLoading){
                onClick()
            }
        }){
            
            if isLoading {
                ProgressView()
                    .animation(.easeInOut,value: isLoading)
                    .padding(5)
                    .background(Color.primaryColor)
                    .cornerRadius(100)
                    .progressViewStyle(CircularProgressViewStyle(tint:.white))
            }else{
                Text(text.uppercased())
                    .animation(.easeInOut,value: isLoading)
                    .padding(.horizontal)
                    .padding(.vertical,5)
                    .font(.body.bold())
                    .background(Color.primaryColor)
                    .foregroundStyle(Color.onPrimary)
                    .cornerRadius(100)
                
            }
        }
        
    }
}

#Preview {
    ProgressButton(text: "Translate", isLoading: true,onClick: {})
}
